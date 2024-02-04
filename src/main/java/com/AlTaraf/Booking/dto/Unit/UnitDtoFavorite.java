package com.AlTaraf.Booking.dto.Unit;

import java.util.List;

public class UnitDtoFavorite {

    private Long unitId;
    private List<String> images; // List of image file paths
    private String nameUnit;
    private String cityName;
    private String regionName;

    private String arabicCityName;
    private String regionArabicName;

    private Boolean favorite;

    // Constructors, getters, and setters

    public UnitDtoFavorite(Long unitId, List<String> images, String nameUnit, String cityName, String regionName, String arabicCityName, String regionArabicName, Boolean favorite) {
        this.unitId = unitId;
        this.images = images;
        this.nameUnit = nameUnit;
        this.cityName = cityName;
        this.regionName = regionName;
        this.arabicCityName = arabicCityName;
        this.regionArabicName = regionArabicName;
        this.favorite = favorite;
    }


    // Getters and setters...

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getArabicCityName() {
        return arabicCityName;
    }

    public void setArabicCityName(String arabicCityName) {
        this.arabicCityName = arabicCityName;
    }

    public String getRegionArabicName() {
        return regionArabicName;
    }

    public void setRegionArabicName(String regionArabicName) {
        this.regionArabicName = regionArabicName;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}