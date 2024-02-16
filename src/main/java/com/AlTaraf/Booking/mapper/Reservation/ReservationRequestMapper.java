package com.AlTaraf.Booking.mapper.Reservation;

import com.AlTaraf.Booking.entity.Reservations;
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
import com.AlTaraf.Booking.payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationRequestMapper {
    @Mapping(source = "clientName", target = "clientName")
    @Mapping(source = "clientPhone", target = "clientPhone")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roomAvailableIds", target = "roomAvailableSet", qualifiedByName = "roomAvailableIdsToEntities")
    @Mapping(source = "availableAreaIds", target = "availableAreaSet", qualifiedByName = "availableAreaIdsToEntities")
    @Mapping(source = "basicFeaturesIds", target = "basicFeaturesSet", qualifiedByName = "basicFeaturesIdsToEntities")
    @Mapping(source = "subFeaturesIds", target = "subFeaturesSet", qualifiedByName = "subFeaturesIdsToEntities")
    @Mapping(source = "foodOptionsIds", target = "foodOptionsSet", qualifiedByName = "foodOptionsIdsToEntities")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "availablePeriodsHallsIds", target = "availablePeriodsHallsSet", qualifiedByName = "availablePeriodsHallsIdsToEntities")
    Reservations toReservation(ReservationRequestDto reservationRequestDto);

    List<Reservations> toReservationsList(List<ReservationRequestDto> reservationRequestDtoList);

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
