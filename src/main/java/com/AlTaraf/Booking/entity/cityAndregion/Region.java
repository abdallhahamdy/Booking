package com.AlTaraf.Booking.entity.cityAndregion;

import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region", nullable = false, unique = true)
    private String regionName;  // Renamed the property to avoid conflict with the entity name

    @Column(name = "arabic_name")
    private String regionArabicName;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("regions")
    private City city;

    public Region() {
    }

    public Region(Long id, String regionName, String regionArabicName, City city) {
        this.id = id;
        this.regionName = regionName;
        this.regionArabicName = regionArabicName;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionArabicName() {
        return regionArabicName;
    }

    public void setRegionArabicName(String regionArabicName) {
        this.regionArabicName = regionArabicName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
