package com.AlTaraf.Booking.mapper.Unit.RoomDetails;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.payload.request.RoomDetails.RoomDetailsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDetailsRequestMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "roomAvailableId", target = "roomAvailable.id")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    RoomDetails toEntity(RoomDetailsRequestDto roomDetailsRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "roomAvailable.id", target = "roomAvailableId")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    RoomDetailsRequestDto toDto(RoomDetails roomDetails);
}
