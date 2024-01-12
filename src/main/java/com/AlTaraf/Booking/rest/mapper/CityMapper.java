package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.rest.dto.CityDto;
import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface CityMapper {

CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "cityName")
    })
    CityDto cityToCityDTO(City city);

    List<CityDto> citiesToCityDTOs(List<City> cities);

}


