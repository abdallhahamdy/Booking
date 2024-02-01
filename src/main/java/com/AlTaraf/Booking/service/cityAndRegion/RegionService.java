package com.AlTaraf.Booking.service.cityAndRegion;

import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.entity.cityAndregion.Region;

import java.util.List;

public interface RegionService {
    List<RegionDto> getRegionsByCityId(Long cityId);
}
