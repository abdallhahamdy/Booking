package com.AlTaraf.Booking.mapper.Unit;


import com.AlTaraf.Booking.dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.payload.response.EventHallsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventHallsMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "city", target = "cityDto")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "featuresHallsSet", target = "featuresHallsDto", qualifiedByName = "mapEntityToFeaturesHallsDto")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHallsDto", qualifiedByName = "mapEntityToAvailablePeriodsHallsDto")
    @Mapping(source = "oldPriceHall", target = "oldPriceHall")
    @Mapping(source = "newPriceHall", target = "newPriceHall")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    EventHallsResponse toEventHallsResponse(Unit unit);

    @Named("mapEntityToFeaturesHallsDto")
    default Set<FeatureForHallsDto> mapEntityToFeaturesHallsDto(Set<FeatureForHalls> featuresHallsSet) {
        return featuresHallsSet.stream()
                .map(feature -> new FeatureForHallsDto(feature.getId(), feature.getName(), feature.getArabicName())) // Assuming FeatureForHallsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    @Named("mapEntityToAvailablePeriodsHallsDto")
    default Set<AvailablePeriodsDto> mapEntityToAvailablePeriodsHallsDto(Set<AvailablePeriods> availablePeriodsHallsSet) {
        return availablePeriodsHallsSet.stream()
                .map(period -> new AvailablePeriodsDto(period.getId(), period.getName(), period.getArabicName())) // Assuming AvailablePeriodsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    List<EventHallsResponse> toEventHallsList(List<Unit> unitList);
}