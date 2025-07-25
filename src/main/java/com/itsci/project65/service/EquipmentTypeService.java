package com.itsci.project65.service;

import com.itsci.project65.model.EquipmentType;


public interface EquipmentTypeService {
    public EquipmentType createEquipmentType(EquipmentType equipmentType);
    public EquipmentType updateEquipmentType(EquipmentType equipmentType);
    public EquipmentType getEquipmentTypeById(int equipmentTypeId);
    public void deleteEquipmentType(int equipmentTypeId);

}
