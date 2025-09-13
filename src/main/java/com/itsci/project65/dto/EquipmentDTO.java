package com.itsci.project65.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private int equipmentId;
    private String equipmentName;
    private String equipmentDetails;
    private String equipmentImg;
    private String equipmentFeature;
    private int price;
}
