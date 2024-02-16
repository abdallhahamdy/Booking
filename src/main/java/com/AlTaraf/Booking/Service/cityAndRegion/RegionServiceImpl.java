package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.RegionMapper;
import com.AlTaraf.Booking.Repository.cityAndregion.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public List<RegionDto> getRegionsByCityId(Long cityId) {
        List<Region> regions = regionRepository.findByCityId(cityId);

        // Use the mapper to convert entities to DTOs
        return regions.stream()
                .map(RegionMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }
}
