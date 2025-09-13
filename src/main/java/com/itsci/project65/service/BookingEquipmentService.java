package com.itsci.project65.service;

import com.itsci.project65.model.BookingEquipment;
import com.itsci.project65.model.BookingEquipmentId;

import java.util.List;

public interface BookingEquipmentService {
    List<BookingEquipment> getAllBookingEquipments();
    List<BookingEquipment> getByBookingId(int bookingId);
    List<BookingEquipment> getByEquipmentId(int equipmentId);
    BookingEquipment addBookingEquipment(BookingEquipment bookingEquipment);
    void deleteBookingEquipment(BookingEquipmentId id);
}
