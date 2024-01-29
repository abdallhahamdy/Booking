package com.AlTaraf.Booking.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
public class CityDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;

    private String arabicCityName;

    public CityDto() {
    }

    public CityDto(Long id, String cityName, String arabicCityName) {
        this.id = id;
        this.cityName = cityName;
        this.arabicCityName = arabicCityName;
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
}
