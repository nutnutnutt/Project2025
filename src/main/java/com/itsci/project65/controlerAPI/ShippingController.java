// แก้ไข package name ใน ShippingController
// จาก: package com.itsci.project65.controlerAPI.ShippingController;
// เป็น: package com.itsci.project65.controlerAPI;

package com.itsci.project65.controlerAPI;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "*")
public class ShippingController {

    @Autowired
    private EquipmentService equipmentService;

    // เพิ่ม test endpoint ง่ายๆ
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Shipping controller is working!");
        response.put("timestamp", LocalDate.now().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculate-shipping")
    public ResponseEntity<Map<String, Object>> calculateShipping(
            @RequestBody Map<String, Object> request) {

        Map<String, Object> response = new HashMap<>();

        // เพิ่ม logging เพื่อ debug
        System.out.println("=== Calculate Shipping Request ===");
        System.out.println("Request received: " + request);

        try {
            // ตรวจสอบ input แต่ละรายการ
            if (request == null) {
                response.put("success", false);
                response.put("message", "Request body is null");
                return ResponseEntity.badRequest().body(response);
            }

            System.out.println("equipmentIds: " + request.get("equipmentIds"));
            System.out.println("deliveryAddress: " + request.get("deliveryAddress"));
            System.out.println("startDate: " + request.get("startDate"));
            System.out.println("endDate: " + request.get("endDate"));

            @SuppressWarnings("unchecked")
            List<Integer> equipmentIds = (List<Integer>) request.get("equipmentIds");
            String deliveryAddress = request.get("deliveryAddress").toString();
            LocalDate startDate = LocalDate.parse(request.get("startDate").toString());
            LocalDate endDate = LocalDate.parse(request.get("endDate").toString());

            // คำนวณจำนวนวันเช่า
            long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            System.out.println("Rental days: " + rentalDays);

            List<Map<String, Object>> equipmentDetails = new ArrayList<>();
            double totalEquipmentCost = 0.0;

            // คำนวณค่าเช่าอุปกรณ์แต่ละรายการ
            for (Integer equipmentId : equipmentIds) {
                Equipment equipment = equipmentService.getEquipmentById(equipmentId);
                System.out.println("Equipment " + equipmentId + ": " +
                        (equipment != null ? equipment.getEquipmentName() : "NOT FOUND"));

                if (equipment != null) {
                    double dailyRate = equipment.getPrice() > 0 ? equipment.getPrice() : 0.0;
                    double equipmentTotal = dailyRate * rentalDays;

                    totalEquipmentCost += equipmentTotal;

                    equipmentDetails.add(Map.of(
                            "equipmentId", equipmentId,
                            "equipmentName", equipment.getEquipmentName(),
                            "dailyRate", dailyRate,
                            "rentalDays", rentalDays,
                            "subtotal", equipmentTotal
                    ));
                }
            }

            // คำนวณค่าจัดส่ง (แบบง่าย)
            double shippingCost = calculateShippingCost(deliveryAddress, equipmentIds.size());
            double totalCost = totalEquipmentCost + shippingCost;

            System.out.println("Equipment cost: " + totalEquipmentCost);
            System.out.println("Shipping cost: " + shippingCost);
            System.out.println("Total cost: " + totalCost);

            Map<String, Object> calculationData = new HashMap<>();
            calculationData.put("equipmentDetails", equipmentDetails);
            calculationData.put("rentalDays", rentalDays);
            calculationData.put("equipmentSubtotal", totalEquipmentCost);
            calculationData.put("shippingCost", shippingCost);
            calculationData.put("totalCost", totalCost);
            calculationData.put("deliveryAddress", deliveryAddress);

            response.put("success", true);
            response.put("message", "คำนวณค่าใช้จ่ายเรียบร้อยแล้ว");
            response.put("data", calculationData);

            System.out.println("=== Response SUCCESS ===");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("=== ERROR ===");
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "เกิดข้อผิดพลาดในการคำนวณ: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private double calculateShippingCost(String address, int itemCount) {
        System.out.println("Calculating shipping for: " + address + ", items: " + itemCount);

        double baseCost = 500.0;
        double perItemCost = 200.0;

        double distanceMultiplier = 1.0;
        if (address.contains("กรุงเทพ") || address.contains("Bangkok")) {
            distanceMultiplier = 1.0;
        } else if (address.contains("ปริมณฑล")) {
            distanceMultiplier = 1.2;
        } else {
            distanceMultiplier = 1.5;
        }

        double result = (baseCost + (perItemCost * itemCount)) * distanceMultiplier;
        System.out.println("Shipping calculation result: " + result);
        return result;
    }
}