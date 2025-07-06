package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="equipment_owner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class EquipmentOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerId", length = 20)
    private int ownerId;

    @Column(name = "ownerUserName", length = 20, nullable = false)
    private String ownerUserName;

    @Column(name = "ownerPassword", length = 16, nullable = false)
    private String ownerPassword;

    @Column(name = "ownerCFPassword", length = 100, nullable = false)
    private String ownerCFPassword;

    @Column(name = "ownerEmail", length = 60, nullable = false)
    private String ownerEmail;

    @Column(name = "ownerFName", length = 50, nullable = false)
    private String ownerFName;

    @Column(name = "ownerLName", length = 50, nullable = false)
    private String ownerLName;

    @Column(name = "ownerGender", nullable = false)
    private String ownerGender;

    private String ownerDOB;

    @Column(name = "ownerTel", length = 10, nullable = false)
    private String ownerTel;

    @Column(name = "ownerHouseNumber", length = 30, nullable = false)
    private String ownerHouseNumber;

    @Column(name = "ownerAlley", length = 20, nullable = false)
    private String ownerAlley;

    @Column(name = "ownerMoo", length = 20, nullable = false)
    private String ownerMoo;

    @Column(name = "ownerSubDistrict", length = 30, nullable = false)
    private String ownerSubDistrict;

    @Column(name = "ownerDistrict", length = 30, nullable = false)
    private String ownerDistrict;

    @Column(name = "ownerProvince", length = 30, nullable = false)
    private String ownerProvince;

    @Column(name = "ownerPostalCode", length = 5, nullable = false)
    private String ownerPostalCode;

}
