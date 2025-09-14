package com.itsci.project65.service.impl;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.dto.EquipmentDTO;
import com.itsci.project65.dto.OwnerBookingDetailDTO;
import com.itsci.project65.dto.BookingEquipmentDetailDTO;
import com.itsci.project65.model.*;
import com.itsci.project65.repository.BookingEquipmentRepository;
import com.itsci.project65.repository.BookingRepository;
import com.itsci.project65.service.OwnerBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerBookingServiceImpl implements OwnerBookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private BookingEquipmentRepository bookingEquipmentRepository;

    @Override
    public List<BookingWithEquipmentDTO> getBookingsByOwnerId(int ownerId) {
        List<BookingWithEquipmentDTO> result = new ArrayList<>();
        
        try {
            // Get all bookings that include equipment owned by this owner
            List<BookingEquipment> bookingEquipments = bookingEquipmentRepository.findByEquipment_EquipmentOwner_OwnerId(ownerId);
            
            // Group by booking ID to avoid duplicates
            List<Integer> bookingIds = bookingEquipments.stream()
                    .map(be -> be.getBooking().getBookingId())
                    .distinct()
                    .collect(Collectors.toList());
            
            for (Integer bookingId : bookingIds) {
                Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
                if (bookingOpt.isPresent()) {
                    Booking booking = bookingOpt.get();
                    Farmer farmer = booking.getFarmer();
                    
                    // Get equipment for this booking
                    List<BookingEquipment> equipmentForBooking = bookingEquipmentRepository.findByBooking_BookingId(bookingId);
                    List<Equipment> equipmentList = equipmentForBooking.stream()
                            .map(BookingEquipment::getEquipment)
                            .collect(Collectors.toList());
                    
                    // Calculate total price
                    int totalPrice = equipmentList.stream()
                            .mapToInt(Equipment::getPrice)
                            .sum();
                    
                    // Create DTO
                    BookingWithEquipmentDTO dto = new BookingWithEquipmentDTO();
                    dto.setBookingId(booking.getBookingId());
                    dto.setBookingStartDate(booking.getBookingstartDate());
                    dto.setBookingEndDate(booking.getBookingendDate());
                    dto.setBookingStatus(booking.getBookingstatus());
                    dto.setBookingchangeAddress(booking.getBookingchangeAddress());
                    
                    // Set farmer information
                    dto.setFarmerId(farmer.getFarmerId());
                    dto.setFarmerName(farmer.getFarmerFName() + " " + farmer.getFarmerLName());
                    dto.setFarmerTel(farmer.getFarmerTel());
                    
                    // Convert Equipment list to EquipmentDTO list
                    List<EquipmentDTO> equipmentDTOList = equipmentList.stream()
                        .map(equipment -> {
                            EquipmentDTO equipmentDTO = new EquipmentDTO();
                            equipmentDTO.setEquipmentId(equipment.getEquipmentId());
                            equipmentDTO.setEquipmentName(equipment.getEquipmentName());
                            equipmentDTO.setEquipmentDetails(equipment.getEquipmentDetails());
                            equipmentDTO.setPrice(equipment.getPrice());
                            equipmentDTO.setEquipmentImg(equipment.getEquipmentImg());
                            equipmentDTO.setEquipmentFeature(equipment.getEquipmentFeature());
                            return equipmentDTO;
                        })
                        .collect(Collectors.toList());
                    
                    dto.setEquipmentList(equipmentDTOList);
                    dto.setTotalPrice(totalPrice);
                    
                    result.add(dto);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching owner bookings: " + e.getMessage());
        }
        
        return result;
    }

    @Override
    public OwnerBookingDetailDTO getBookingDetailById(int bookingId) {
        try {
            Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                return null;
            }
            
            Booking booking = bookingOpt.get();
            Farmer farmer = booking.getFarmer();
            
            // Get equipment for this booking
            List<BookingEquipment> bookingEquipments = bookingEquipmentRepository.findByBooking_BookingId(bookingId);
            List<BookingEquipmentDetailDTO> equipmentDetailList = new ArrayList<>();
            int totalPrice = 0;
            
            for (BookingEquipment be : bookingEquipments) {
                Equipment equipment = be.getEquipment();
                BookingEquipmentDetailDTO equipmentDetail = new BookingEquipmentDetailDTO();
                equipmentDetail.setEquipmentId(equipment.getEquipmentId());
                equipmentDetail.setEquipmentName(equipment.getEquipmentName());
                equipmentDetail.setEquipmentDetails(equipment.getEquipmentDetails());
                equipmentDetail.setEquipmentFeature(equipment.getEquipmentFeature());
                equipmentDetail.setPrice(equipment.getPrice());
                equipmentDetail.setEquipmentStatus(equipment.getEquipmentStatus());
                equipmentDetail.setEquipmentAddress(equipment.getEquipmentAddress());
                equipmentDetail.setEquipmentImg(equipment.getEquipmentImg());
                
                if (equipment.getEquipmentType() != null) {
                    equipmentDetail.setEquipmentTypeName(equipment.getEquipmentType().getEquipmentTypeName());
                }
                
                equipmentDetailList.add(equipmentDetail);
                totalPrice += equipment.getPrice();
            }
            
            // Create detailed DTO
            OwnerBookingDetailDTO detailDTO = new OwnerBookingDetailDTO();
            detailDTO.setBookingId(booking.getBookingId());
            detailDTO.setBookingstartDate(booking.getBookingstartDate());
            detailDTO.setBookingendDate(booking.getBookingendDate());
            detailDTO.setBookingchangeAddress(booking.getBookingchangeAddress());
            detailDTO.setBookingstatus(booking.getBookingstatus());
            detailDTO.setFarmerId(farmer.getFarmerId());
            detailDTO.setFarmerName(farmer.getFarmerFName() + " " + farmer.getFarmerLName());
            detailDTO.setFarmerTel(farmer.getFarmerTel());
            detailDTO.setFarmerEmail(farmer.getFarmerEmail());
            detailDTO.setEquipmentList(equipmentDetailList);
            detailDTO.setTotalPrice(totalPrice);
            
            // TODO: Add payment slip path when implemented
            detailDTO.setPaymentSlipPath(null);
            
            return detailDTO;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching booking detail: " + e.getMessage());
        }
    }

    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        try {
            Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                return false;
            }
            
            Booking booking = bookingOpt.get();
            booking.setBookingstatus(status);
            bookingRepository.save(booking);
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating booking status: " + e.getMessage());
        }
    }

    @Override
    public boolean isBookingBelongsToOwner(int bookingId, int ownerId) {
        try {
            List<BookingEquipment> bookingEquipments = bookingEquipmentRepository.findByBooking_BookingId(bookingId);
            
            // Check if any equipment in this booking belongs to the owner
            return bookingEquipments.stream()
                    .anyMatch(be -> be.getEquipment().getEquipmentOwner().getOwnerId() == ownerId);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
