package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.Translator;
import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    private Translator translator;

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<CityDto>> getAllCities(@RequestParam("lang") String lang) {
        System.out.println("Received lang parameter: " + lang);

        List<CityDto> cities = cityService.getAllCities();

        cities.forEach(cityDto -> {
            cityDto.setCityName("city.name." + cityDto.getCityName());
        });

        Locale locale = new Locale(lang);
        cities.forEach(cityDto -> {
            System.out.println("CityNameKey before translation: " + cityDto.getCityName());
            cityDto.setCityName(translator.toLocale(cityDto.getCityName(), locale));
            System.out.println("CityNameKey after translation: " + cityDto.getCityName());
        });

        if (cities.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(cities);
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
