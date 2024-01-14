package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.mapper.CityMapper;
import com.AlTaraf.Booking.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    // Constructor
    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.citiesToCityDTOs(cities);
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.findByCity(cityName).orElse(null);
    }
}