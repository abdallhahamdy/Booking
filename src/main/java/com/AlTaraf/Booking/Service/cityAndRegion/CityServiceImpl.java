package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.saveCityDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.CityMapper;
import com.AlTaraf.Booking.Mapper.RegionMapper;
import com.AlTaraf.Booking.Repository.cityAndregion.CityRepository;
import com.AlTaraf.Booking.Repository.cityAndregion.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CityMapper cityMapper;

    @Transactional
    public City saveCityWithRegions(saveCityDto saveCityDto) {
        City city = new City();
        city.setCityName(saveCityDto.getCityName());
        city.setArabicCityName(saveCityDto.getArabicCityName());
//        List<RegionDto> regions = cityDto.getRegions();
//        if (regions != null && !regions.isEmpty()) {
//            regions.forEach(region -> {
//                region.setCity(city);
//                city.getRegions().add(region);
//            });
//        }

        return cityRepository.save(city);
    }

    @Override
    public Optional<City> getCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }
//    @Transactional
//    public Region addRegionToCity(Long cityId, RegionDto regionDto) {
//        // Fetch the city
//        City city = cityRepository.findById(cityId)
//                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));
//
//        // Use the mapper to convert RegionDto to Region
//        Region newRegion = RegionMapper.INSTANCE.dtoToEntity(regionDto);
//
//        // Associate the region with the city
//        newRegion.setCity(city);
//        city.getRegions().add(newRegion);
//
//        // Save the updated city (which includes the new region)
//        cityRepository.save(city);
//
//        // Return the added region
//        return newRegion;
//    }

    @Transactional
    public Region updateRegionInCity(Long cityId, Long regionId, RegionDto RegionDto) {
        // Fetch the city
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));

        // Fetch the region within the city
        Region region = regionRepository.findByIdAndCityId(regionId, cityId)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + regionId));

        // Use the mapper to update properties of the region
        Region updatedRegion = RegionMapper.INSTANCE.updateDtoToEntity(RegionDto, region);

        // Save the updated region
        return regionRepository.save(updatedRegion);
    }
//    @Override
//    public CityDto saveCity(CityDto cityDto) {
//        City city = cityMapper.cityDTOToCity(cityDto);
//        City savedCity = cityRepository.save(city);
//        return cityMapper.cityToCityDTO(savedCity);
//    }


//    @Override
//    public CityDto getCityByName(String cityName) {
//        Optional<City> optionalCity = cityRepository.findByCity(cityName);
//        return optionalCity.map(cityMapper::cityToCityDTO).orElse(null);
//    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.citiesToCityDTOs(cities);
    }


//    @Override
//    public CityDto updateCity(Long id, CityDto cityDto) {
//        City existingCity = cityRepository.findById(id).orElse(null);
//
//        if (existingCity != null) {
//            existingCity.setCity(cityDto.getCityName());
//            City updatedCity = cityRepository.save(existingCity);
//            return cityMapper.cityToCityDTO(updatedCity);
//        }
//
//        return null; // Or throw an exception if needed
//    }

//    @Override
//    public void deleteCity(Long id) {
//        cityRepository.deleteById(id);
//    }
//
//    @Override
//    public CityDto getCityById(Long id) {
//        Optional<City> cityOptional = cityRepository.findById(id);
//        return cityOptional.map(cityMapper::cityToCityDTO).orElse(null);
//    }
}