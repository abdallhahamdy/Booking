package com.AlTaraf.Booking.Mapper.Reservation;

import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import com.AlTaraf.Booking.Payload.response.Unit.UnitGeneralResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationGetByIdMapper {
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "clientName", target = "clientName")
    @Mapping(source = "clientPhone", target = "clientPhone")
    @Mapping(source = "roomAvailable", target = "roomAvailable")
    @Mapping(source = "availableArea", target = "availableArea")
    @Mapping(source = "basicFeaturesSet", target = "basicFeatures")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "foodOptionsSet", target = "foodOptions")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHalls")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    ReservationResponseGetId toResponseDto(Reservations reservations);


}
