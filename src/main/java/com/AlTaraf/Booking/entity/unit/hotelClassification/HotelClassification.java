package com.AlTaraf.Booking.entity.unit.hotelClassification;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_classification")
public class HotelClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOTEL_CLASSIFICATION_ID")
    private Long id;

    @Column(name = "HOTEL_CLASSIFICATION_NAME")
    private String name;

    @Column(name = "HOTEL_CLASSIFICATION_ARABIC_NAME")
    private String arabicName;

    public HotelClassification() {
    }

    public HotelClassification(Long id, String name, String arabicName) {
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
