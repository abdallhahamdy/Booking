package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.mapper.CityMapper;
import com.AlTaraf.Booking.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public CityDto saveCity(CityDto cityDto) {
        City city = cityMapper.cityDTOToCity(cityDto);
        City savedCity = cityRepository.save(city);
        return cityMapper.cityToCityDTO(savedCity);
    }


    @Override
    public CityDto getCityByName(String cityName) {
        Optional<City> optionalCity = cityRepository.findByCity(cityName);
        return optionalCity.map(cityMapper::cityToCityDTO).orElse(null);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.citiesToCityDTOs(cities);
    }

    @Override
    public CityDto updateCity(Long id, CityDto cityDto) {
        City existingCity = cityRepository.findById(id).orElse(null);

        if (existingCity != null) {
            existingCity.setCity(cityDto.getCityName());
            City updatedCity = cityRepository.save(existingCity);
            return cityMapper.cityToCityDTO(updatedCity);
        }

        return null; // Or throw an exception if needed
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public CityDto getCityById(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.map(cityMapper::cityToCityDTO).orElse(null);
    }
}