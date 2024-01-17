package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    // Constructor
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cities = cityService.getAllCities();
        if (cities.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(cities);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto cityDto) {
        CityDto savedCityDto = cityService.saveCity(cityDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCityDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
        CityDto existingCity = cityService.getCityById(id);

        if (existingCity != null) {
            existingCity.setCityName(cityDto.getCityName());  // Assuming cityName property in CityDto
            CityDto updatedCityDto = cityService.saveCity(existingCity);

            return ResponseEntity.ok(updatedCityDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        CityDto existingCity = cityService.getCityById(id);

        if (existingCity != null) {
            cityService.deleteCity(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
