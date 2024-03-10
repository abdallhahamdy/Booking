package com.AlTaraf.Booking.Payload.response.Unit;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
// Residencies
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitResidenciesResponseDto {

    private Long unitId;
    private Long unitTypeId;
    private List<String> imagePaths;
    private String nameUnit;
    private String description;

    private CityDto cityDto;
    private RegionDto regionDto;
    private AccommodationType accommodationType;

    private List<ImageData> images;
    private HotelClassification hotelClassification;
    private Set<RoomAvailable> roomAvailables;
    private Set<Feature> features;
    private Set<SubFeature> subFeatures;
    private Set<FoodOption> foodOptions;
    private Set<AvailableArea> availableAreas;

    private Double latForMapping;
    private Double longForMapping;
    private String evaluationName;
    private String evaluationArabicName;
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
}
