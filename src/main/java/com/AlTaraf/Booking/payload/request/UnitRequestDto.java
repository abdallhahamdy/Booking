package com.AlTaraf.Booking.payload.request;

public class UnitRequestDto {
    private Long id;
    private Long unitTypeId;
    private Long userId;
    private Long accommodationTypeId;

    private String nameUnit;

    private String description;

    private Long cityId;
    private Long regionId;

    private Long hotelClassificationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccommodationTypeId() {
        return accommodationTypeId;
    }

    public void setAccommodationTypeId(Long accommodationTypeId) {
        this.accommodationTypeId = accommodationTypeId;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getHotelClassificationId() {
        return hotelClassificationId;
    }

    public void setHotelClassificationId(Long hotelClassificationId) {
        this.hotelClassificationId = hotelClassificationId;
    }
}
