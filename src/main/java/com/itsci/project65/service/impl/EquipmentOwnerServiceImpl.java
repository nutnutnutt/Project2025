package com.itsci.project65.service.impl;

import com.itsci.project65.model.EquipmentOwner;
import com.itsci.project65.repository.EquipmentOwnerRepository;
import com.itsci.project65.service.EquipmentOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentOwnerServiceImpl implements EquipmentOwnerService {

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;

    @Override
    public EquipmentOwner createEquipmentOwner(EquipmentOwner equipmentOwner) {
        return equipmentOwnerRepository.save(equipmentOwner);
    }

    @Override
    public EquipmentOwner updateEquipmentOwner(EquipmentOwner equipmentOwner) {
        Optional<EquipmentOwner> existingOwner = equipmentOwnerRepository.findById(equipmentOwner.getOwnerId());
        if (existingOwner.isPresent()) {
            return equipmentOwnerRepository.save(equipmentOwner);
        } else {
            throw new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการแก้ไข (ID: " + equipmentOwner.getOwnerId() + ")");
        }
    }

    @Override
    public EquipmentOwner getEquipmentOwnerById(int ownerId) {
        return equipmentOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการค้นหา (ID: " + ownerId + ")"));
    }

    @Override
    public void deleteEquipmentOwner(int ownerId) {
        Optional<EquipmentOwner> existingOwner = equipmentOwnerRepository.findById(ownerId);
        if (existingOwner.isPresent()) {
            equipmentOwnerRepository.delete(existingOwner.get());
        } else {
            throw new RuntimeException("ไม่พบเจ้าของอุปกรณ์ที่ต้องการลบ (ID: " + ownerId + ")");
        }
    }
}
