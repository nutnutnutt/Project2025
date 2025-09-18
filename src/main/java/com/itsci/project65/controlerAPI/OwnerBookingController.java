package com.itsci.project65.controlerAPI;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.dto.OwnerBookingDetailDTO;
import com.itsci.project65.service.OwnerBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerBookingController {

    @Autowired
    private OwnerBookingService ownerBookingService;

    /**
     * Get all bookings for equipment owned by this owner
     * @param ownerId Owner ID
     * @return List of bookings with equipment details
     */
    @GetMapping("/bookings/{ownerId}")
    public ResponseEntity<?> getOwnerBookings(@PathVariable int ownerId) {
        try {
            List<BookingWithEquipmentDTO> bookings = ownerBookingService.getBookingsByOwnerId(ownerId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถดึงข้อมูลการจองได้", "message", e.getMessage()));
        }
    }

    /**
     * Get detailed booking information by booking ID
     * @param bookingId Booking ID
     * @return Detailed booking information with equipment and farmer details
     */
    @GetMapping("/bookings/detail/{bookingId}")
    public ResponseEntity<?> getBookingDetail(@PathVariable int bookingId) {
        try {
            OwnerBookingDetailDTO bookingDetail = ownerBookingService.getBookingDetailById(bookingId);
            if (bookingDetail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบข้อมูลการจองที่ระบุ"));
            }
            return ResponseEntity.ok(bookingDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถดึงรายละเอียดการจองได้", "message", e.getMessage()));
        }
    }

    /**
     * Update booking status
     * @param bookingId Booking ID
     * @param statusRequest Status update request
     * @return Updated booking information
     */
    @PutMapping("/bookings/status/{bookingId}")
    public ResponseEntity<?> updateBookingStatus(
            @PathVariable int bookingId,
            @RequestBody Map<String, String> statusRequest) {
        try {
            String newStatus = statusRequest.get("status");
            if (newStatus == null || newStatus.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "กรุณาระบุสถานะที่ต้องการอัพเดท"));
            }

            boolean updated = ownerBookingService.updateBookingStatus(bookingId, newStatus);
            if (updated) {
                return ResponseEntity.ok(Map.of("message", "อัพเดทสถานะเรียบร้อยแล้ว", "status", newStatus));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบการจองที่ระบุ"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถอัพเดทสถานะได้", "message", e.getMessage()));
        }
    }

    /**
     * Approve booking request (before payment)
     * @param bookingId Booking ID
     * @return Response message
     */
    @PutMapping("/bookings/approve-booking/{bookingId}")
    public ResponseEntity<?> approveBooking(@PathVariable int bookingId) {
        try {
            boolean approved = ownerBookingService.updateBookingStatus(bookingId, "confirmed");
            if (approved) {
                return ResponseEntity.ok(Map.of("message", "อนุมัติการจองเรียบร้อยแล้ว กรุณารอการชำระเงินจากลูกค้า"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบการจองที่ระบุ"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถอนุมัติการจองได้", "message", e.getMessage()));
        }
    }

    /**
     * Reject booking request (before payment)
     * @param bookingId Booking ID
     * @return Response message
     */
    @PutMapping("/bookings/reject-booking/{bookingId}")
    public ResponseEntity<?> rejectBooking(@PathVariable int bookingId) {
        try {
            boolean rejected = ownerBookingService.updateBookingStatus(bookingId, "cancelled");
            if (rejected) {
                return ResponseEntity.ok(Map.of("message", "ปฏิเสธการจองเรียบร้อยแล้ว"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบการจองที่ระบุ"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถปฏิเสธการจองได้", "message", e.getMessage()));
        }
    }

    /**
     * Approve payment for booking
     * @param bookingId Booking ID
     * @return Response message
     */
    @PutMapping("/bookings/approve-payment/{bookingId}")
    public ResponseEntity<?> approvePayment(@PathVariable int bookingId) {
        try {
            boolean approved = ownerBookingService.updateBookingStatus(bookingId, "approvepayment");
            if (approved) {
                return ResponseEntity.ok(Map.of("message", "อนุมัติการชำระเงินเรียบร้อยแล้ว"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบการจองที่ระบุ"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถอนุมัติการชำระเงินได้", "message", e.getMessage()));
        }
    }

    /**
     * Reject payment for booking
     * @param bookingId Booking ID
     * @return Response message
     */
    @PutMapping("/bookings/reject-payment/{bookingId}")
    public ResponseEntity<?> rejectPayment(@PathVariable int bookingId) {
        try {
            boolean rejected = ownerBookingService.updateBookingStatus(bookingId, "rejectpayment");
            if (rejected) {
                return ResponseEntity.ok(Map.of("message", "ปฏิเสธการชำระเงินเรียบร้อยแล้ว"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "ไม่พบการจองที่ระบุ"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "ไม่สามารถปฏิเสธการชำระเงินได้", "message", e.getMessage()));
        }
    }
}
