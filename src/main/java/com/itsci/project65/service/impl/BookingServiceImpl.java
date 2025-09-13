package com.itsci.project65.service.impl;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.dto.EquipmentDTO;
import com.itsci.project65.model.*;
import com.itsci.project65.repository.BookingEquipmentRepository;
import com.itsci.project65.repository.BookingRepository;
import com.itsci.project65.repository.EquipmentRepository;
import com.itsci.project65.repository.FarmerRepository;
import com.itsci.project65.request.BookingRequest;
import com.itsci.project65.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    BookingEquipmentRepository bookingEquipmentRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    FarmerRepository farmerRepository;

    @Override
    public Booking createBooking(Booking booking) {


        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getBookingId());
        if (optionalBooking.isPresent()) {
            Booking existingBooking = optionalBooking.get();
            existingBooking.setBookingstartDate(booking.getBookingstartDate());
            existingBooking.setBookingendDate(booking.getBookingendDate());
            existingBooking.setBookingchangeAddress(booking.getBookingchangeAddress());
            existingBooking.setBookingstatus(booking.getBookingstatus());
            existingBooking.setFarmer(booking.getFarmer());
            return bookingRepository.save(existingBooking);
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการแก้ไข (ID: " + booking.getBookingId() + ")");
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการค้นหา (ID: " + bookingId + ")"));
    }

    @Override
    public void deleteBooking(int bookingId) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            bookingRepository.delete(existingBooking.get());
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการลบ (ID: " + bookingId + ")");
        }
    }

    @Override
    public Booking createBookingWithEquipment(BookingRequest request,int farmerId) {
        Booking booking = new Booking();
        booking.setBookingstartDate(request.getBookingStartDate());
        booking.setBookingendDate(request.getBookingEndDate());
        booking.setBookingstatus("pending");
        booking.setBookingchangeAddress(request.getChangeAddress());
        Farmer farmer = farmerRepository.getReferenceById(farmerId);
        booking.setFarmer(farmer);

        booking = bookingRepository.save(booking);
        // 2. เพิ่ม BookingEquipment
        for (Integer eqId : request.getEquipmentIds()) {
            Equipment eq = equipmentRepository.findById(eqId)
                    .orElseThrow(() -> new RuntimeException("Equipment not found: " + eqId));

            BookingEquipment be = new BookingEquipment();
            be.setBooking(booking);
            be.setEquipment(eq);
            // กำหนด Composite Key
            be.setId(new BookingEquipmentId(booking.getBookingId(), eq.getEquipmentId()));

            bookingEquipmentRepository.save(be);
        }

        return booking;
    }

    @Override
    public List<BookingWithEquipmentDTO> getBookingWithEquipmentByFarmer(int farmerId) {
        List<Booking> bookings = bookingRepository.findByFarmer_FarmerIdAndBookingstatus(farmerId,"pending");
        List<BookingWithEquipmentDTO> dtoList = new ArrayList<>();

        for (Booking b : bookings) {
            List<EquipmentDTO> equipmentDTOs = b.getBookingEquipments().stream()
                    .map(be -> new EquipmentDTO(
                            be.getEquipment().getEquipmentId(),
                            be.getEquipment().getEquipmentName(),
                            be.getEquipment().getEquipmentDetails(),
                            be.getEquipment().getEquipmentImg(),
                            be.getEquipment().getEquipmentFeature(),
                            be.getEquipment().getPrice()
                    ))
                    .toList();

            BookingWithEquipmentDTO dto = new BookingWithEquipmentDTO(
                    b.getBookingId(),
                    b.getBookingstartDate(),
                    b.getBookingendDate(),
                    b.getBookingstatus(),
                    b.getBookingchangeAddress(),
                    equipmentDTOs
            );

            dtoList.add(dto);
        }
        return dtoList;
    }




}

