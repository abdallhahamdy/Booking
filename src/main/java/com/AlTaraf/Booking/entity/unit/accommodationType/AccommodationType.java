package com.AlTaraf.Booking.entity.unit.accommodationType;

import jakarta.persistence.*;

@Entity
@Table(name = "accommodation_type")
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOMMODATION_TYPE_ID")
    private Long id;
    @Column(name = "ACCOMMODATION_NAME")
    private String name;
    @Column(name = "ACCOMMODATION_ARABIC_NAME")
    private String arabicName;

    public AccommodationType() {
    }

    public AccommodationType(Long id, String name, String arabicName) {
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
