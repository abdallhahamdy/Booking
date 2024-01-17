package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.dto.CityDto;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "city", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    CityDto cityToCityDTO(City city);

    @Mapping(source = "cityName", target = "city")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    City cityDTOToCity(CityDto cityDto);


    @Mapping(source = "city", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    List<CityDto> citiesToCityDTOs(List<City> cities);

}


