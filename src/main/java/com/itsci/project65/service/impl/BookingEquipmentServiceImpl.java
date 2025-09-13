package com.itsci.project65.service.impl;

import com.itsci.project65.model.BookingEquipment;
import com.itsci.project65.model.BookingEquipmentId;
import com.itsci.project65.repository.BookingEquipmentRepository;
import com.itsci.project65.service.BookingEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingEquipmentServiceImpl implements BookingEquipmentService {

    @Autowired
    private BookingEquipmentRepository bookingEquipmentRepository;

    @Override
    public List<BookingEquipment> getAllBookingEquipments() {
        return bookingEquipmentRepository.findAll();
    }

    @Override
    public List<BookingEquipment> getByBookingId(int bookingId) {
        return bookingEquipmentRepository.findByBooking_BookingId(bookingId);
    }

    @Override
    public List<BookingEquipment> getByEquipmentId(int equipmentId) {
        return bookingEquipmentRepository.findByEquipment_EquipmentId(equipmentId);
    }

    @Override
    public BookingEquipment addBookingEquipment(BookingEquipment bookingEquipment) {
        return bookingEquipmentRepository.save(bookingEquipment);
    }

    @Override
    public void deleteBookingEquipment(BookingEquipmentId id) {
        bookingEquipmentRepository.deleteById(id);
    }
}
