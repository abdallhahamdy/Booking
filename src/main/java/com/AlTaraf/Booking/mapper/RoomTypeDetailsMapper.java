//package com.AlTaraf.Booking.mapper;
//
//
//import com.AlTaraf.Booking.dto.RoomTypeDetailsDTO;
//import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomTypeDetails;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface RoomTypeDetailsMapper {
//    RoomTypeDetailsMapper INSTANCE = Mappers.getMapper(RoomTypeDetailsMapper.class);
//
//    @Mapping(source = "unit.id", target = "unitId")
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "roomType", target = "roomType")
//    @Mapping(source = "number", target = "number")
//    @Mapping(source = "oldPrice", target = "oldPrice")
//    RoomTypeDetailsDTO roomTypeDetailsToDTO(RoomTypeDetails roomTypeDetails);
//
//    @Mapping(target = "unit.id", source = "unitId")
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "roomType", target = "roomType")
//    @Mapping(source = "number", target = "number")
//    @Mapping(source = "oldPrice", target = "oldPrice")
//    RoomTypeDetails DTOToRoomTypeDetails(RoomTypeDetailsDTO roomTypeDetailsDTO);
//}