package com.itsci.project65.controler;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/equipment")
public class EquipmentControler {

    @Autowired
    private EquipmentService equipmentService;

    private final String IMAGE_UPLOAD_DIR = System.getProperty("user.dir") + "/images/"; // กำหนดโฟลเดอร์เก็บรูปภาพ


    @PostMapping("/create")
    public ResponseEntity<?> createEquipment(
            @RequestParam("equipmentName") String equipmentName,
            @RequestParam("equipmentDetails") String equipmentDetails,
            @RequestParam("equipmentFeature") String equipmentFeature,
            @RequestParam("equipmentList") String equipmentList,
            @RequestParam("equipmentAddress") String equipmentAddress,
            @RequestParam("price") double price,
            @RequestParam("equipmentStatus") String equipmentStatus,
            @RequestParam("owner_id") int ownerId,
            @RequestParam(value = "equipmentImg", required = false) MultipartFile file
    ) {
        try {
            String fileName = null;

            if (file != null && !file.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                File uploadDir = new File(IMAGE_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                file.transferTo(new File(IMAGE_UPLOAD_DIR + fileName));
            }

            Equipment equipment = new Equipment();
            equipment.setEquipmentName(equipmentName);
            equipment.setEquipmentDetails(equipmentDetails);
            equipment.setEquipmentFeature(equipmentFeature);
            equipment.setEquipmentList(equipmentList);
            equipment.setEquipmentAddress(equipmentAddress);
            equipment.setPrice((int) price);
            equipment.setEquipmentStatus(equipmentStatus);
            equipment.setOwner_id(ownerId);
            equipment.setEquipmentImg(fileName);

            Equipment createdEquipment = equipmentService.createEquipment(equipment);
            return new ResponseEntity<>(createdEquipment, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File Upload Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable("id") int id) {
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEquipment(
            @PathVariable("id") int id,
            @RequestParam("equipmentName") String equipmentName,
            @RequestParam("equipmentDetails") String equipmentDetails,
            @RequestParam("equipmentFeature") String equipmentFeature,
            @RequestParam("equipmentList") String equipmentList,
            @RequestParam("equipmentAddress") String equipmentAddress,
            @RequestParam("price") double price,
            @RequestParam("equipmentStatus") String equipmentStatus,
            @RequestParam("owner_id") int ownerId,
            @RequestParam(value = "equipmentImg", required = false) MultipartFile file
    ) {
        try {
            Equipment existingEquipment = equipmentService.getEquipmentById(id);
            if (existingEquipment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found");
            }

            String fileName = existingEquipment.getEquipmentImg();

            if (file != null && !file.isEmpty()) {
                fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                File uploadDir = new File(IMAGE_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                file.transferTo(new File(IMAGE_UPLOAD_DIR + fileName));
            }

            existingEquipment.setEquipmentName(equipmentName);
            existingEquipment.setEquipmentDetails(equipmentDetails);
            existingEquipment.setEquipmentFeature(equipmentFeature);
            existingEquipment.setEquipmentList(equipmentList);
            existingEquipment.setEquipmentAddress(equipmentAddress);
            existingEquipment.setPrice((int) price);
            existingEquipment.setEquipmentStatus(equipmentStatus);
            existingEquipment.setOwner_id(ownerId);
            existingEquipment.setEquipmentImg(fileName);

            Equipment updatedEquipment = equipmentService.updateEquipment(existingEquipment);
            return new ResponseEntity<>(updatedEquipment, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File Upload Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") int id) {
        try {
            equipmentService.deleteEquipment(id);
            return new ResponseEntity<>("Deleted equipment successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
