package com.AlTaraf.Booking.Mapper.Unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Payload.response.RoomDetails.RoomDetailsResponseDto;
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
