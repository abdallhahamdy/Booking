package com.AlTaraf.Booking.entity.unit.AvailablePeriods;

import jakarta.persistence.*;

@Table(name = "available_periods")
@Entity
public class AvailablePeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_periods_id")
    private Long id;

    @Column(name = "AVAILABLE_PERIODS_NAME")
    private String name;

    @Column(name = "AVAILABLE_PERIODS_ARABIC_NAME")
    private String arabicName;

    public AvailablePeriods() {
    }

    public AvailablePeriods(Long id, String name, String arabicName) {
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
