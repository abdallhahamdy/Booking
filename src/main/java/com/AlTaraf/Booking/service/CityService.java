package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.entity.City;

import java.util.List;

public interface CityService {
    City getCityByName(String cityName);
    List<CityDto> getAllCities();
}
