package com.itsci.project65.service.impl;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.repository.EquipmentRepository;
import com.itsci.project65.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Equipment equipment) {

        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipment.getEquipmentId());
        if (existingEquipment.isPresent()) {
            return equipmentRepository.save(equipment);
        } else {
            throw new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการแก้ไข (ID: " + equipment.getEquipmentId() + ")");
        }
    }

    @Override
    public Equipment getEquipmentById(int equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการค้นหา (ID: " + equipmentId + ")"));
    }

    @Override
    public void deleteEquipment(int equipmentId) {
        Optional<Equipment> existingEquipment = equipmentRepository.findById(equipmentId);
        if (existingEquipment.isPresent()) {
            equipmentRepository.delete(existingEquipment.get());
        } else {
            throw new RuntimeException("ไม่พบอุปกรณ์ที่ต้องการลบ (ID: " + equipmentId + ")");
        }
    }


}
