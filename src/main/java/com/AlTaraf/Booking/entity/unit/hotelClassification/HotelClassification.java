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
    private String hotelClassificationName;

    @Column(name = "HOTEL_CLASSIFICATION_ARABIC_NAME")
    private String hotelClassificationArabicName;

    public HotelClassification() {
    }

    public HotelClassification(Long id, String hotelClassificationName, String hotelClassificationArabicName) {
        this.id = id;
        this.hotelClassificationName = hotelClassificationName;
        this.hotelClassificationArabicName = hotelClassificationArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelClassificationName() {
        return hotelClassificationName;
    }

    public void setHotelClassificationName(String hotelClassificationName) {
        this.hotelClassificationName = hotelClassificationName;
    }

    public String getHotelClassificationArabicName() {
        return hotelClassificationArabicName;
    }

    public void setHotelClassificationArabicName(String hotelClassificationArabicName) {
        this.hotelClassificationArabicName = hotelClassificationArabicName;
    }
}
