package com.AlTaraf.Booking.Payload.response.Unit;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// General
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitGeneralResponseDto {
    private Long unitId;
    private UnitType unitType;
    private String nameUnit;
    private String description;
    private CityDtoSample cityDtoSample;
    private RegionDto regionDto;

    private AccommodationType accommodationType;

    private List<ImageDataDTO> images;

    private HotelClassification hotelClassification;

    private Set<RoomAvailable> roomAvailables;

    private Set<AvailableArea> availableAreas;

    private Set<Feature> features;

    private Set<SubFeature> subFeatures;

    private Set<FoodOption> foodOptions;

    private int capacityHalls;

    private Set<FeatureForHallsDto> featuresHallsDto;
    private Set<AvailablePeriodsDto> availablePeriodsHallsDto;

//    private int oldPriceHall;
//    private int newPriceHall;

    private Double latForMapping;
    private Double longForMapping;
    private int oldPriceHall;
    private int newPriceHall;
    private int price;
    private String evaluationName;
    private String evaluationArabicName;
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
    private int commission;
}
