package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.repository.CityRepository;
import com.AlTaraf.Booking.rest.dto.CityDto;
import com.AlTaraf.Booking.rest.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return CityMapper.INSTANCE.citiesToCityDTOs(cities);
    }

}
