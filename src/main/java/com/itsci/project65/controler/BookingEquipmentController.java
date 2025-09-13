package com.itsci.project65.controler;

import com.itsci.project65.model.BookingEquipment;
import com.itsci.project65.model.BookingEquipmentId;
import com.itsci.project65.service.BookingEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-equipment")
public class BookingEquipmentController {

    @Autowired
    private BookingEquipmentService bookingEquipmentService;

    // ✅ ดึงความสัมพันธ์ทั้งหมด
    @GetMapping
    public List<BookingEquipment> getAllBookingEquipments() {
        return bookingEquipmentService.getAllBookingEquipments();
    }

    // ✅ ดึงทั้งหมดของ bookingId หนึ่ง ๆ
    @GetMapping("/booking/{bookingId}")
    public List<BookingEquipment> getByBookingId(@PathVariable int bookingId) {
        return bookingEquipmentService.getByBookingId(bookingId);
    }

    // ✅ ดึงทั้งหมดของ equipmentId หนึ่ง ๆ
    @GetMapping("/equipment/{equipmentId}")
    public List<BookingEquipment> getByEquipmentId(@PathVariable int equipmentId) {
        return bookingEquipmentService.getByEquipmentId(equipmentId);
    }

    // ✅ เพิ่มการจองอุปกรณ์เข้า booking
    @PostMapping
    public BookingEquipment addBookingEquipment(@RequestBody BookingEquipment bookingEquipment) {
        return bookingEquipmentService.addBookingEquipment(bookingEquipment);
    }

    // ✅ ลบความสัมพันธ์ booking ↔ equipment
    @DeleteMapping("/{bookingId}/{equipmentId}")
    public void deleteBookingEquipment(@PathVariable int bookingId, @PathVariable int equipmentId) {
        BookingEquipmentId id = new BookingEquipmentId(bookingId, equipmentId);
        bookingEquipmentService.deleteBookingEquipment(id);
    }
}