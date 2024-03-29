package com.AlTaraf.Booking.Controller.cityAndregion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.saveCityDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.city.CityMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.cityAndRegion.CityService;
import com.AlTaraf.Booking.Service.cityAndRegion.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    RegionService regionService;

//    @PostMapping("/save")
//    public ResponseEntity<?> saveCityWithRegions(@RequestBody saveCityDto saveCityDto) {
//        City savedCity = cityService.saveCityWithRegions(saveCityDto);
//        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
//    }

    @PostMapping("add")
    public ResponseEntity<?> createCity(@RequestBody CityDto cityDto) {
        try {
            CityDto createdCity = cityService.createCity(cityDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, "created city is Sucessful "));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "created city failed "));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable("id") Long cityId) {
        try {
            cityService.deleteCity(cityId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete city");
        }
    }

    @DeleteMapping("/region/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable("id") Long regionId) {
        try {
            regionService.deleteRegion(regionId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "delete region is successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Failed to delete region"));
        }
    }

    @PostMapping("{cityId}/add-region")
    public ResponseEntity<RegionDto> addRegionToCity(@PathVariable("cityId") Long cityId, @RequestBody RegionDto regionDto) {
        try {
            RegionDto addedRegion = regionService.addRegionToCity(cityId, regionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedRegion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @PostMapping("/{Id}/regions")
//    public ResponseEntity<Region> addRegionToCity(
//            @PathVariable Long Id,
//            @RequestBody RegionDto regionDto) {
//
//        Region addedRegion = cityService.addRegionToCity(Id, regionDto);
//        return new ResponseEntity<>(addedRegion, HttpStatus.CREATED);
//    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCities() {
        List<CityDto> cities = cityService.getAllCities();
        if (cities.isEmpty()) {
            ApiResponse response = new ApiResponse(204, "No Content for Cities!");

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else {
            return ResponseEntity.ok(cities);
        }
    }

    @PutMapping("/{cityId}/regions/{regionId}")
    public ResponseEntity<Region> updateRegionInCity(
            @PathVariable Long cityId,
            @PathVariable Long regionId,
            @RequestBody RegionDto RegionDto) {

        Region updatedRegion = cityService.updateRegionInCity(cityId, regionId, RegionDto);
        return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
    }

    @PutMapping("/cities/{cityId}/English-Name")
    public ResponseEntity<?> updateCityEnglishName(
            @PathVariable Long cityId,
            @RequestParam String newName
    ) {
        Optional<City> optionalCity = cityService.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setCityName(newName);
            cityService.save(city);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cities/{cityId}/Arabic-Name")
    public ResponseEntity<?> updateCityArabicName(
            @PathVariable Long cityId,
            @RequestParam String newName
    ) {
        Optional<City> optionalCity = cityService.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setArabicCityName(newName);
            cityService.save(city);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
//        CityDto cityDto = cityService.getCityById(id);
//        if (cityDto != null) {
//            return ResponseEntity.ok(cityDto);
//        } else {
//            ApiResponse response = new ApiResponse(404, "Not Found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createCity(@RequestBody CityDto cityDto) {
//        cityService.saveCity(cityDto);
//        ApiResponse response = new ApiResponse(200, "Added City successfully!");
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
//        CityDto existingCity = cityService.getCityById(id);
//
//        if (existingCity != null) {
//            existingCity.setCityName(cityDto.getCityName());  // Assuming cityName property in CityDto
//            existingCity.setArabicCityName(cityDto.getArabicCityName());
//            cityService.saveCity(existingCity);
//            ApiResponse response = new ApiResponse(205, "Reset Content successfully!");
//
//            return ResponseEntity.ok(response);
//        } else {
//            ApiResponse response = new ApiResponse(404, "Not Found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
//        CityDto existingCity = cityService.getCityById(id);
//
//        if (existingCity != null) {
//            cityService.deleteCity(id);
//            ApiResponse response = new ApiResponse(200, "Role deleted successfully!");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } else {
//            ApiResponse response = new ApiResponse(404, "Not Found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }
}
