package com.itsci.project65.dto;

import java.time.LocalDate;
import java.util.List;

public class OwnerBookingDetailDTO {
    private int bookingId;
    private LocalDate bookingstartDate;
    private LocalDate bookingendDate;
    private String bookingchangeAddress;
    private String bookingstatus;
    
    // Farmer information
    private int farmerId;
    private String farmerName;
    private String farmerTel;
    private String farmerEmail;
    
    // Equipment list
    private List<BookingEquipmentDetailDTO> equipmentList;
    
    // Payment slip information
    private String paymentSlipPath;
    
    // Total price
    private int totalPrice;

    // Constructors
    public OwnerBookingDetailDTO() {}

    public OwnerBookingDetailDTO(int bookingId, LocalDate bookingstartDate, LocalDate bookingendDate, 
                                String bookingchangeAddress, String bookingstatus, int farmerId, 
                                String farmerName, String farmerTel, String farmerEmail, 
                                List<BookingEquipmentDetailDTO> equipmentList, String paymentSlipPath, 
                                int totalPrice) {
        this.bookingId = bookingId;
        this.bookingstartDate = bookingstartDate;
        this.bookingendDate = bookingendDate;
        this.bookingchangeAddress = bookingchangeAddress;
        this.bookingstatus = bookingstatus;
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.farmerTel = farmerTel;
        this.farmerEmail = farmerEmail;
        this.equipmentList = equipmentList;
        this.paymentSlipPath = paymentSlipPath;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getBookingstartDate() {
        return bookingstartDate;
    }

    public void setBookingstartDate(LocalDate bookingstartDate) {
        this.bookingstartDate = bookingstartDate;
    }

    public LocalDate getBookingendDate() {
        return bookingendDate;
    }

    public void setBookingendDate(LocalDate bookingendDate) {
        this.bookingendDate = bookingendDate;
    }

    public String getBookingchangeAddress() {
        return bookingchangeAddress;
    }

    public void setBookingchangeAddress(String bookingchangeAddress) {
        this.bookingchangeAddress = bookingchangeAddress;
    }

    public String getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerTel() {
        return farmerTel;
    }

    public void setFarmerTel(String farmerTel) {
        this.farmerTel = farmerTel;
    }

    public String getFarmerEmail() {
        return farmerEmail;
    }

    public void setFarmerEmail(String farmerEmail) {
        this.farmerEmail = farmerEmail;
    }

    public List<BookingEquipmentDetailDTO> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<BookingEquipmentDetailDTO> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getPaymentSlipPath() {
        return paymentSlipPath;
    }

    public void setPaymentSlipPath(String paymentSlipPath) {
        this.paymentSlipPath = paymentSlipPath;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
