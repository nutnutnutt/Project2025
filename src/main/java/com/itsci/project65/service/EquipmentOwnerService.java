package com.itsci.project65.service;

import com.itsci.project65.model.EquipmentOwner;

public interface EquipmentOwnerService {
    public EquipmentOwner createEquipmentOwner(EquipmentOwner equipmentOwner);
    public EquipmentOwner updateEquipmentOwner(EquipmentOwner equipmentOwner);
    public EquipmentOwner getEquipmentOwnerById(int ownerId);
    public void deleteEquipmentOwner(int ownerId);
}

