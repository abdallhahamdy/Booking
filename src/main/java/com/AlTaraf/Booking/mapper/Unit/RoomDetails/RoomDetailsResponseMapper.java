package com.AlTaraf.Booking.mapper.Unit.RoomDetails;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.payload.response.RoomDetails.RoomDetailsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDetailsResponseMapper {

    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    RoomDetails toEntity(RoomDetailsResponseDto roomDetailsResponseDto);

    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    RoomDetailsResponseDto toDto(RoomDetails roomDetails);

}
