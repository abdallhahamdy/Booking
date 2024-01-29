package com.AlTaraf.Booking.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false, unique = true)
    private String cityName;  // Renamed the property to avoid conflict with the entity name

    @Column(name = "arabic_name")
    private String arabicCityName;

    @OneToMany(mappedBy = "city")  // Refers to the 'city' property in the User entity
    private Set<User> users = new HashSet<>();

    public City() {
    }

    public City(Long id, String cityName, String arabicCityName, Set<User> users) {
        this.id = id;
        this.cityName = cityName;
        this.arabicCityName = arabicCityName;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArabicCityName() {
        return arabicCityName;
    }

    public void setArabicCityName(String arabicCityName) {
        this.arabicCityName = arabicCityName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
