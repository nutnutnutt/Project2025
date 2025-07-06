package com.itsci.project65.controler;

import com.itsci.project65.model.Booking;
import com.itsci.project65.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingControler {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ไม่สามารถเพิ่มข้อมูลการจองได้: " + e.getMessage());
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


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking) {
        try {
            Booking existingBooking = bookingService.getBookingById(id);
            if (existingBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลการจองที่ต้องการแก้ไข");
            }

            existingBooking.setBookingendDate(booking.getBookingendDate());
            existingBooking.setBookingstartDate(booking.getBookingstartDate());
            existingBooking.setBookingList(booking.getBookingList());
            existingBooking.setBookingchangeAddress(booking.getBookingchangeAddress());
            existingBooking.setBookingstatus(booking.getBookingstatus());
            existingBooking.setFarmer(booking.getFarmer());

            Booking updatedBooking = bookingService.updateBooking(existingBooking);
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

