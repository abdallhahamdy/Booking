package com.AlTaraf.Booking.service.cityAndRegion;

import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.saveCityDto;
import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.cityAndregion.Region;

import java.util.List;

public interface CityService {
//    CityDto getCityByName(String cityName);
    List<CityDto> getAllCities();

    City saveCityWithRegions(saveCityDto cityDto);

    Region updateRegionInCity(Long cityId, Long regionId, RegionDto RegionDto);

    Region addRegionToCity(Long cityId, RegionDto regionDto);
//    CityDto saveCity(CityDto cityDto);
//    CityDto updateCity(Long id, CityDto cityDto);
//    void deleteCity(Long id);

//    CityDto getCityById(Long id);

}

