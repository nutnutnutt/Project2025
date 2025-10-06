package com.itsci.project65.controlerAPI;

import com.itsci.project65.model.Booking;
import com.itsci.project65.model.Equipment;
import com.itsci.project65.service.BookingService;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/equipment")
public class Equipmentcontroller {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired(required = false) // ใส่ required = false ไว้ก่อน
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addEquipment(@RequestBody Equipment equipment) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Passing null for the file as this legacy endpoint doesn't support file uploads.
            Equipment createdEquipment = equipmentService.createEquipment(equipment, null);
            response.put("success", true);
            response.put("message", "Equipment added successfully");
            response.put("data", createdEquipment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to add equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<Map<String, Object>> getAllEquipments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Equipment> equipments = equipmentService.getAllEquipments();
            response.put("success", true);
            response.put("message", "Equipments retrieved successfully");
            response.put("data", equipments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve equipments: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getEquipmentById(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            response.put("success", true);
            response.put("message", "Equipment retrieved successfully");
            response.put("data", equipment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEquipment(@PathVariable("id") int id, @RequestBody Equipment equipment) {
        Map<String, Object> response = new HashMap<>();
        try {
            equipment.setEquipmentId(id);
            Equipment updatedEquipment = equipmentService.updateEquipment(equipment);
            response.put("success", true);
            response.put("message", "Equipment updated successfully");
            response.put("data", updatedEquipment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteEquipment(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            equipmentService.deleteEquipment(id);
            response.put("success", true);
            response.put("message", "Equipment deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete equipment: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{equipmentId}/availability")
    public ResponseEntity<Map<String, Object>> getEquipmentAvailability(
            @PathVariable int equipmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, Object> response = new HashMap<>();
        try {
            Equipment equipment = equipmentService.getEquipmentById(equipmentId);

            // ตรวจสอบว่าจองล่วงหน้าอย่างน้อย 3 วัน
            LocalDate today = LocalDate.now();
            LocalDate minBookingDate = today.plusDays(3);

            if (startDate.isBefore(minBookingDate)) {
                response.put("success", false);
                response.put("message", "กรุณาจองล่วงหน้าอย่างน้อย 3 วัน");
                response.put("minBookingDate", minBookingDate.toString());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // ตรวจสอบการจองที่มีอยู่จริง
            List<Booking> existingBookings = bookingService.getBookingsByEquipmentAndDateRange(equipmentId, startDate, endDate);

            boolean isAvailable = existingBookings.isEmpty();
            List<String> blockedDates = new ArrayList<>();
            List<String> availableDates = new ArrayList<>();

            // สร้างรายการวันที่
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                String dateStr = currentDate.toString();

                // แก้ไข lambda expression - ทำให้ variable เป็น final
                final LocalDate checkDate = currentDate;
                boolean dateBlocked = existingBookings.stream().anyMatch(booking ->
                        !checkDate.isBefore(booking.getBookingstartDate()) &&
                                !checkDate.isAfter(booking.getBookingendDate())
                );

                if (dateBlocked) {
                    blockedDates.add(dateStr);
                } else {
                    availableDates.add(dateStr);
                }

                currentDate = currentDate.plusDays(1);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("equipmentId", equipmentId);
            data.put("equipmentName", equipment.getEquipmentName());
            data.put("available", isAvailable);
            data.put("availableDates", availableDates);
            data.put("blockedDates", blockedDates);
            data.put("minBookingDate", minBookingDate.toString());
            data.put("existingBookings", existingBookings.size());

            response.put("success", true);
            response.put("message", "Equipment availability retrieved successfully");
            response.put("data", data);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to check availability: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{equipmentId}/calendar")
    public ResponseEntity<Map<String, Object>> getEquipmentCalendar(
            @PathVariable int equipmentId,
            @RequestParam(required = false) String month) { // format: YYYY-MM

        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("=== Equipment Calendar Request ===");
            System.out.println("Equipment ID: " + equipmentId);
            System.out.println("Month: " + month);

            Equipment equipment = equipmentService.getEquipmentById(equipmentId);

            // ถ้าไม่ระบุเดือน ใช้เดือนปัจจุบัน
            LocalDate targetDate = month != null ?
                    LocalDate.parse(month + "-01") : LocalDate.now();

            List<Map<String, Object>> calendarBookings = new ArrayList<>();

            try {
                // ดึงข้อมูลการจองจริงจาก BookingService
                List<Booking> bookings = bookingService.getBookingsByEquipmentAndMonth(equipmentId, targetDate);

                System.out.println("Found " + bookings.size() + " bookings for equipment " + equipmentId);

                for (Booking booking : bookings) {
                    Map<String, Object> bookingMap = new HashMap<>();
                    bookingMap.put("startDate", booking.getBookingstartDate().toString());
                    bookingMap.put("endDate", booking.getBookingendDate().toString());
                    bookingMap.put("status", booking.getBookingstatus());
                    bookingMap.put("bookingId", booking.getBookingId());

                    // ดึงชื่อ farmer
                    String farmerName = "ไม่ระบุชื่อ";
                    if (booking.getFarmer() != null) {
                        farmerName = booking.getFarmer().getFarmerFName();
                    }
                    bookingMap.put("farmerName", farmerName);

                    calendarBookings.add(bookingMap);

                    System.out.println("Booking: " + booking.getBookingId() +
                            " from " + booking.getBookingstartDate() +
                            " to " + booking.getBookingendDate() +
                            " by " + farmerName);
                }

            } catch (Exception e) {
                System.out.println("Error getting real booking data: " + e.getMessage());
                e.printStackTrace();
                // ถ้า error ให้ใช้ข้อมูลว่าง
            }

            Map<String, Object> data = new HashMap<>();
            data.put("equipmentId", equipmentId);
            data.put("equipmentName", equipment.getEquipmentName());
            data.put("month", targetDate.toString().substring(0, 7)); // YYYY-MM
            data.put("bookings", calendarBookings);
            data.put("totalBookings", calendarBookings.size());

            response.put("success", true);
            response.put("message", "Equipment calendar retrieved successfully");
            response.put("data", data);

            System.out.println("Calendar response: " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("=== Calendar Error ===");
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to retrieve calendar: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
