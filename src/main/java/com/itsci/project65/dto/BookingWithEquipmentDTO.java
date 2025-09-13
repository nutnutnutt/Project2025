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

    private List<EquipmentDTO> equipmentList;
}
