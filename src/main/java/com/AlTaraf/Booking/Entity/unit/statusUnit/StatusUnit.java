package com.AlTaraf.Booking.Entity.unit.statusUnit;

import jakarta.persistence.*;

@Entity
@Table(name = "status_unit")
public class StatusUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long id;

    @Column(name = "STATUS_NAME")
    private String name;

    @Column(name = "STATUS_ARABIC_NAME")
    private String arabicName;

    public StatusUnit() {
    }

    public StatusUnit(Long id, String name, String arabicName) {
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
