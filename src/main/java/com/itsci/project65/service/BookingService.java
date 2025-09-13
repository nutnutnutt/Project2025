package com.itsci.project65.service;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.model.Booking;
import com.itsci.project65.model.Equipment;
import com.itsci.project65.request.BookingRequest;

import java.util.List;
import java.util.Map;

public interface BookingService {
    public Booking createBooking(Booking booking);
    public Booking updateBooking(Booking booking);
    public Booking getBookingById(int bookingId);
    public void deleteBooking(int bookingId);

    Booking createBookingWithEquipment(BookingRequest request,int farmerId);
    List<BookingWithEquipmentDTO> getBookingWithEquipmentByFarmer(int farmerId);

}

