package com.itsci.project65.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingRequest {
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private String bookingStatus;
    private String changeAddress;
    private List<Integer> equipmentIds;  // list ของ equipmentId
}

