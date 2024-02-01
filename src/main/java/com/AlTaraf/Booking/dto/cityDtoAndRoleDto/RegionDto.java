package com.AlTaraf.Booking.dto.cityDtoAndRoleDto;

public class RegionDto {
    private Long id;
    private String regionName;
    private String regionArabicName;

    // getters and setters

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
}