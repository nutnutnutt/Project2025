package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private LocalDate bookingstartDate;

    private LocalDate bookingendDate;

    @Column(name = "bookingchangeAddress", length = 255)
    private String bookingchangeAddress;

    @Column(name = "bookingstatus", length = 50)
    private String bookingstatus;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private Delivery delivery;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private Review review;
    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingEquipment> bookingEquipments = new ArrayList<>();

}
