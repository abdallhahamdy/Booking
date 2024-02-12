package com.AlTaraf.Booking.mapper.Unit;

import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitRequestMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitTypeId", target = "unitType.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "regionId", target = "region.id")
    @Mapping(source = "accommodationTypeId", target = "accommodationType", qualifiedByName = "mapAccommodationTypeIdToEntity")
    @Mapping(source = "hotelClassificationId", target = "hotelClassification", qualifiedByName = "mapHotelClassificationIdToEntity")
    @Mapping(source = "roomAvailableIds", target = "roomAvailableSet", qualifiedByName = "roomAvailableIdsToEntities")
    @Mapping(source = "availableAreaIds", target = "availableAreaSet", qualifiedByName = "availableAreaIdsToEntities")
    @Mapping(source = "basicFeaturesIds", target = "basicFeaturesSet", qualifiedByName = "basicFeaturesIdsToEntities")
    @Mapping(source = "subFeaturesIds", target = "subFeaturesSet", qualifiedByName = "subFeaturesIdsToEntities")
    @Mapping(source = "foodOptionsIds", target = "foodOptionsSet", qualifiedByName = "foodOptionsIdsToEntities")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "featuresHallsIds", target = "featuresHallsSet", qualifiedByName = "featuresHallsIdsToEntities")
    @Mapping(source = "availablePeriodsHallsIds", target = "availablePeriodsHallsSet", qualifiedByName = "availablePeriodsHallsIdsToEntities")
    @Mapping(source = "oldPriceHall", target = "oldPriceHall")
    @Mapping(source = "newPriceHall", target = "newPriceHall")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    Unit toUnit(UnitRequestDto unitRequestDto);

    List<Unit> toUnitList(List<UnitRequestDto> unitRequestDtos);

    @Named("mapAccommodationTypeIdToEntity")
    static AccommodationType mapAccommodationTypeIdToEntity(Long accommodationTypeId) {
        if (accommodationTypeId == null) {
            return null;
        }
        AccommodationType accommodationType = new AccommodationType();
        accommodationType.setId(accommodationTypeId);
        return accommodationType;
    }

    @Named("mapHotelClassificationIdToEntity")
    static HotelClassification mapHotelClassificationIdToEntity(Long hotelClassificationId) {
        if (hotelClassificationId == null) {
            return null;
        }
        HotelClassification hotelClassification = new HotelClassification();
        hotelClassification.setId(hotelClassificationId);
        return hotelClassification;
    }

    @Named("roomAvailableIdsToEntities")
    static Set<RoomAvailable> roomAvailableIdsToEntities(Set<Long> roomAvailableIds) {
        if (roomAvailableIds == null) {
            return Collections.emptySet();
        }
        return roomAvailableIds.stream()
                .map(id -> {
                    RoomAvailable roomAvailable = new RoomAvailable();
                    roomAvailable.setId(id);
                    return roomAvailable;
                })
                .collect(Collectors.toSet());
    }

    @Named("availableAreaIdsToEntities")
    static Set<AvailableArea> availableAreaIdsToEntities(Set<Long> availableAreaIds) {
        if (availableAreaIds == null) {
            return Collections.emptySet();
        }
        return availableAreaIds.stream()
                .map(id -> {
                    AvailableArea availableArea = new AvailableArea();
                    availableArea.setId(id);
                    return availableArea;
                })
                .collect(Collectors.toSet());
    }

    @Named("basicFeaturesIdsToEntities")
    static Set<Feature> basicFeaturesIdsToEntities(Set<Long> basicFeaturesIds) {
        if (basicFeaturesIds == null) {
            return Collections.emptySet();
        }
        return basicFeaturesIds.stream()
                .map(id -> {
                    Feature feature = new Feature();
                    feature.setId(id);
                    return feature;
                })
                .collect(Collectors.toSet());
    }

    @Named("subFeaturesIdsToEntities")
    static Set<SubFeature> subFeaturesIdsToEntities(Set<Long> subFeaturesIds) {
        if (subFeaturesIds == null) {
            return Collections.emptySet();
        }
        return subFeaturesIds.stream()
                .map(id -> {
                    SubFeature subFeature = new SubFeature();
                    subFeature.setId(id);
                    return subFeature;
                })
                .collect(Collectors.toSet());
    }

    @Named("foodOptionsIdsToEntities")
    static Set<FoodOption> foodOptionsIdsToEntities(Set<Long> foodOptionsIds) {
        if (foodOptionsIds == null) {
            return Collections.emptySet();
        }
        return foodOptionsIds.stream()
                .map(id -> {
                    FoodOption foodOption = new FoodOption();
                    foodOption.setId(id);
                    return foodOption;
                })
                .collect(Collectors.toSet());
    }

    @Named("featuresHallsIdsToEntities")
    static Set<FeatureForHalls> featuresHallsIdsToEntities(Set<Long> featuresHallsIds) {
        if (featuresHallsIds == null) {
            return Collections.emptySet();
        }
        return featuresHallsIds.stream()
                .map(id -> {
                    FeatureForHalls featureForHalls = new FeatureForHalls();
                    featureForHalls.setId(id);
                    return featureForHalls;
                })
                .collect(Collectors.toSet());
    }

    @Named("availablePeriodsHallsIdsToEntities")
    static Set<AvailablePeriods> availablePeriodsHallsToEntities(Set<Long> availablePeriodsHallsIds) {
        if (availablePeriodsHallsIds == null) {
            return Collections.emptySet();
        }
        return availablePeriodsHallsIds.stream()
                .map(id -> {
                    AvailablePeriods availablePeriods = new AvailablePeriods();
                    availablePeriods.setId(id);
                    return availablePeriods;
                })
                .collect(Collectors.toSet());
    }
}
