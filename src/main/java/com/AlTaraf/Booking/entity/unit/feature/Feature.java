package com.AlTaraf.Booking.entity.unit.feature;

import jakarta.persistence.*;

@Entity
@Table(name ="feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEATURE_NAME")
    private String name;

    @Column(name = "FEATURE_ARABIC_NAME")
    private String arabicName;

    public Feature() {
    }

    public Feature(Long id, String name, String arabicName) {
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
