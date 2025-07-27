package com.itsci.project65.service.impl;

import com.itsci.project65.model.Equipment;
import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.repository.EquipmentOwnerRepository;
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

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;

    @Override
    public Equipment createEquipment(Equipment equipment) {
        if (equipment.getEquipmentOwner() != null && equipment.getEquipmentOwner().getOwnerId() > 0) {
            EquipmentOwner owner = equipmentOwnerRepository.findById(equipment.getEquipmentOwner().getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found with id: " + equipment.getEquipmentOwner().getOwnerId()));
            equipment.setEquipmentOwner(owner);
        }
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Equipment equipment) {
        Optional<Equipment> existingEquipmentOpt = equipmentRepository.findById(equipment.getEquipmentId());
        if (existingEquipmentOpt.isPresent()) {
            Equipment existingEquipment = existingEquipmentOpt.get();

            // Update fields from the request
            existingEquipment.setEquipmentName(equipment.getEquipmentName());
            existingEquipment.setEquipmentList(equipment.getEquipmentList());
            existingEquipment.setPrice(equipment.getPrice());
            existingEquipment.setEquipmentDetails(equipment.getEquipmentDetails());
            existingEquipment.setEquipmentStatus(equipment.getEquipmentStatus());
            existingEquipment.setEquipmentFeature(equipment.getEquipmentFeature());
            existingEquipment.setEquipmentAddress(equipment.getEquipmentAddress());
            existingEquipment.setEquipmentImg(equipment.getEquipmentImg());
            existingEquipment.setViewsReviews(equipment.getViewsReviews());

            if (equipment.getEquipmentOwner() != null && equipment.getEquipmentOwner().getOwnerId() > 0) {
                EquipmentOwner owner = equipmentOwnerRepository.findById(equipment.getEquipmentOwner().getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Owner not found with id: " + equipment.getEquipmentOwner().getOwnerId()));
                existingEquipment.setEquipmentOwner(owner);
            }

            return equipmentRepository.save(existingEquipment);
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

    @Override
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }


}
