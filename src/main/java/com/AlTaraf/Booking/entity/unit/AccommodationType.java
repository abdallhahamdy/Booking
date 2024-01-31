package com.AlTaraf.Booking.entity.unit;

import jakarta.persistence.*;

@Entity
@Table(name = "accommodation_type")
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOMMODATION_TYPE_ID")
    private Long id;
    @Column(name = "ACCOMMODATION_NAME")
    private String AccommodationName;
    @Column(name = "ACCOMMODATION_ARABIC_NAME")
    private String AccommodationArabicName;

    public AccommodationType() {
    }

    public AccommodationType(Long id, String accommodationName, String accommodationArabicName) {
        this.id = id;
        AccommodationName = accommodationName;
        AccommodationArabicName = accommodationArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccommodationName() {
        return AccommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        AccommodationName = accommodationName;
    }

    public String getAccommodationArabicName() {
        return AccommodationArabicName;
    }

    public void setAccommodationArabicName(String accommodationArabicName) {
        AccommodationArabicName = accommodationArabicName;
    }
}
