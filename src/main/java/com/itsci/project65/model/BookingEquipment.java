package com.itsci.project65.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "booking_equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEquipment {
    @EmbeddedId
    private BookingEquipmentId id;

    @ManyToOne
    @MapsId("bookingId")  // ใช้ id จาก EmbeddedId
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @MapsId("equipmentId")
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
}
