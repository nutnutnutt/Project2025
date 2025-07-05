package com.itsci.project65.controler;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentControler {

    @Autowired
    private EquipmentService equipmentService;


    @PostMapping("/create")
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment createdEquipment = equipmentService.createEquipment(equipment);
            if (createdEquipment == null) {
                return new ResponseEntity<>("Cannot add Equipment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(createdEquipment, HttpStatus.CREATED);
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


    @PutMapping("/update")
    public ResponseEntity<?> updateEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment updatedEquipment = equipmentService.updateEquipment(equipment);
            return new ResponseEntity<>(updatedEquipment, HttpStatus.OK);
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
