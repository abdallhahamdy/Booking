package com.AlTaraf.Booking.payload.response.Unit;

import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
// Residencies
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitResidenciesResponseDto {

    private Long unitId;
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
}
