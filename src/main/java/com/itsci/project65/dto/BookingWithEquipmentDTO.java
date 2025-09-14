package com.itsci.project65.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingWithEquipmentDTO {

    private int bookingId;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private String bookingStatus;
    private String bookingchangeAddress;
    
    // Farmer information
    private int farmerId;
    private String farmerName;
    private String farmerTel;
    
    // Equipment and pricing
    private List<EquipmentDTO> equipmentList;
    private int totalPrice;
}
