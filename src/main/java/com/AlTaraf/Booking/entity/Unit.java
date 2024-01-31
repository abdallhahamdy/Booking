package com.AlTaraf.Booking.entity;

import com.AlTaraf.Booking.entity.enums.UnitType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    @Column(name = "NAME_UNIT")
    private String nameUnit;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdultsAllowed() {
        return adultsAllowed;
    }

    public void setAdultsAllowed(int adultsAllowed) {
        this.adultsAllowed = adultsAllowed;
    }

    public int getChildrenAllowed() {
        return childrenAllowed;
    }

    public void setChildrenAllowed(int childrenAllowed) {
        this.childrenAllowed = childrenAllowed;
    }
}
