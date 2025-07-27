package com.itsci.project65.service;

import com.itsci.project65.model.Equipment;

import java.util.List;

public interface EquipmentService {
    public Equipment createEquipment(Equipment equipment);
    public Equipment updateEquipment(Equipment equipment);
    public Equipment getEquipmentById(int equipmentId);
    public void deleteEquipment(int equipmentId);
    public List<Equipment> getAllEquipments();

}
