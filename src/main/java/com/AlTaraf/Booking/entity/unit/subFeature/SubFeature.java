package com.AlTaraf.Booking.entity.unit.subFeature;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_feature")
public class SubFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUB_FEATURE_NAME")
    private String name;

    @Column(name = "SUB_FEATURE_ARABIC_NAME")
    private String arabicName;

    public SubFeature() {
    }

    public SubFeature(Long id, String name, String arabicName) {
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
