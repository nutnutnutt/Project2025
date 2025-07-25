package com.itsci.project65.model;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="farmers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farmerId", length = 20)
    private int farmerId;

    @NotBlank
    @Column(name = "farmerUserName", length = 20, nullable = false)
    private String farmerUserName;

    @NotBlank
    @Column(name = "farmerPassword", length = 16, nullable = false)
    private String farmerPassword;

    @NotBlank
    @Column(name = "farmerCFPassword", length = 100, nullable = false)
    private String farmerCFPassword;

    @NotBlank
    @Column(name = "farmerEmail", length = 60, nullable = false)
    private String farmerEmail;

    @NotBlank
    @Column(name = "farmerFName", length = 50, nullable = false)
    private String farmerFName;

    @NotBlank
    @Column(name = "farmerLName", length = 50, nullable = false)
    private String farmerLName;

    @NotBlank
    @Column(name = "farmerGender", length = 10, nullable = false)
    private String farmerGender;

    private String farmerDOB;

    @NotBlank
    @Column(name = "farmerTel", length = 10, nullable = false)
    private String farmerTel;

    @NotBlank
    @Column(name = "farmerHouseNumber", length = 30, nullable = false)
    private String farmerHouseNumber;

    @NotBlank
    @Column(name = "farmerAlley", length = 20, nullable = false)
    private String farmerAlley;

    @NotBlank
    @Column(name = "farmerMoo", length = 20, nullable = false)
    private String farmerMoo;

    @NotBlank
    @Column(name = "farmerSubDistrict", length = 30, nullable = false)
    private String farmerSubDistrict;

    @NotBlank
    @Column(name = "farmersDistrict", length = 30, nullable = false)
    private String farmerDistrict;

    @NotBlank
    @Column(name = "farmerProvince", length = 30, nullable = false)
    private String farmerProvince;

    @NotBlank
    @Column(name = "farmerPostalCode", length = 5, nullable = false)
    private String farmerPostalCode;

    @JsonIgnore
    @OneToMany(mappedBy = "farmer" ,cascade = CascadeType.ALL)
    private List<Address> address;

}
