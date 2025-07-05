package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="equipment_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentTypeId;

    @Column(name = "equipmentTypeName", length = 100, nullable = false)
    private String equipmentTypeName;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
}
