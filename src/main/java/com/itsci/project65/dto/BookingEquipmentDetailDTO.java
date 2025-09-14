package com.itsci.project65.dto;

public class BookingEquipmentDetailDTO {
    private int equipmentId;
    private String equipmentName;
    private String equipmentDetails;
    private String equipmentFeature;
    private int price;
    private String equipmentStatus;
    private String equipmentAddress;
    private String equipmentImg;
    private String equipmentTypeName;

    // Constructors
    public BookingEquipmentDetailDTO() {}

    public BookingEquipmentDetailDTO(int equipmentId, String equipmentName, String equipmentDetails, 
                                   String equipmentFeature, int price, String equipmentStatus, 
                                   String equipmentAddress, String equipmentImg, String equipmentTypeName) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentDetails = equipmentDetails;
        this.equipmentFeature = equipmentFeature;
        this.price = price;
        this.equipmentStatus = equipmentStatus;
        this.equipmentAddress = equipmentAddress;
        this.equipmentImg = equipmentImg;
        this.equipmentTypeName = equipmentTypeName;
    }

    // Getters and Setters
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentDetails() {
        return equipmentDetails;
    }

    public void setEquipmentDetails(String equipmentDetails) {
        this.equipmentDetails = equipmentDetails;
    }

    public String getEquipmentFeature() {
        return equipmentFeature;
    }

    public void setEquipmentFeature(String equipmentFeature) {
        this.equipmentFeature = equipmentFeature;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public String getEquipmentAddress() {
        return equipmentAddress;
    }

    public void setEquipmentAddress(String equipmentAddress) {
        this.equipmentAddress = equipmentAddress;
    }

    public String getEquipmentImg() {
        return equipmentImg;
    }

    public void setEquipmentImg(String equipmentImg) {
        this.equipmentImg = equipmentImg;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }
}
