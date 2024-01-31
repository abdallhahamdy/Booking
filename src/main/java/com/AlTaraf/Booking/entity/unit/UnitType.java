package com.AlTaraf.Booking.entity.unit;

import jakarta.persistence.*;

@Entity
@Table(name = "unit_type")
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_TYPE_ID")
    private Long id;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "TYPE_ARABIC_NAME")
    private String typeArabicName;

    public UnitType() {
    }

    public UnitType(Long id, String typeName, String typeArabicName) {
        this.id = id;
        this.typeName = typeName;
        this.typeArabicName = typeArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeArabicName() {
        return typeArabicName;
    }

    public void setTypeArabicName(String typeArabicName) {
        this.typeArabicName = typeArabicName;
    }
}