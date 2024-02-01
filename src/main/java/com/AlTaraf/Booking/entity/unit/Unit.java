package com.AlTaraf.Booking.entity.unit;

import jakarta.persistence.*;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "UNIT_TYPE_ID", nullable = false)
    private UnitType unitType;
    @ManyToOne
    @JoinColumn(name = "ACCOMMODATION_TYPE_ID", nullable = false)
    private AccommodationType accommodationType;
    @Column(name = "NAME_UNIT")
    private String nameUnit;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "HOTEL_CLASSIFICATION_ID", nullable = false)
    private HotelClassification hotelClassification;
    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;
    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    public Unit() {
    }

    public Unit(Long id, UnitType unitType, AccommodationType accommodationType, String nameUnit, String description, HotelClassification hotelClassification, int adultsAllowed, int childrenAllowed) {
        this.id = id;
        this.unitType = unitType;
        this.accommodationType = accommodationType;
        this.nameUnit = nameUnit;
        this.description = description;
        this.hotelClassification = hotelClassification;
        this.adultsAllowed = adultsAllowed;
        this.childrenAllowed = childrenAllowed;
    }

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

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
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

    public HotelClassification getHotelClassification() {
        return hotelClassification;
    }

    public void setHotelClassification(HotelClassification hotelClassification) {
        this.hotelClassification = hotelClassification;
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
