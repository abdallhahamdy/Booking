package com.AlTaraf.Booking.entity.unit.unitType;

import jakarta.persistence.*;

@Entity
@Table(name = "unit_type")
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_TYPE_ID")
    private Long id;

    @Column(name = "TYPE_NAME")
    private String name;

    @Column(name = "TYPE_ARABIC_NAME")
    private String arabicName;

    public UnitType() {
    }

    public UnitType(Long id, String name, String arabicName) {
        this.id = id;
        this.name = name;
        this.arabicName = arabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }
}