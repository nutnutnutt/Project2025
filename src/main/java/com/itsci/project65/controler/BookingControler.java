package com.itsci.project65.controler;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.model.Booking;
import com.itsci.project65.model.Equipment;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.request.BookingRequest;
import com.itsci.project65.service.BookingService;
import com.itsci.project65.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingControler {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/create-with-equipment")
    public Booking createBookingWithEquipment(@RequestBody BookingRequest request,
                                              @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        int farmerId = jwtUtil.extractFarmerId(token);
        return bookingService.createBookingWithEquipment(request,farmerId);
    }

    @GetMapping("/bookings-with-equipment")
    public ResponseEntity<List<BookingWithEquipmentDTO>> getBookingsWithEquipmentByFarmer(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            int farmerId = jwtUtil.extractFarmerId(token);
            List<BookingWithEquipmentDTO> result = bookingService.getBookingWithEquipmentByFarmer(farmerId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") int id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/bookings-with-equipment/{id}")
    public ResponseEntity<BookingWithEquipmentDTO> getBookingWithEquipmentById(@PathVariable("id") int id) {
        try {
            BookingWithEquipmentDTO result = bookingService.getBookingWithEquipmentById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking) {
        try {
            booking.setBookingId(id);
            Booking updatedBooking = bookingService.updateBooking(booking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") int id) {
        try {
            bookingService.deleteBooking(id);
            return new ResponseEntity<>("ลบข้อมูลการจองเรียบร้อย", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

