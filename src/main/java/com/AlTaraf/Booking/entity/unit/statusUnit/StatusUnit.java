package com.AlTaraf.Booking.entity.unit.statusUnit;

import jakarta.persistence.*;

@Entity
@Table(name = "status_unit")
public class StatusUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long id;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "STATUS_ARABIC_NAME")
    private String statusArabicName;

    public StatusUnit() {
    }

    public StatusUnit(Long id, String statusName, String statusArabicName) {
        this.id = id;
        this.statusName = statusName;
        this.statusArabicName = statusArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusArabicName() {
        return statusArabicName;
    }

    public void setStatusArabicName(String statusArabicName) {
        this.statusArabicName = statusArabicName;
    }
}
