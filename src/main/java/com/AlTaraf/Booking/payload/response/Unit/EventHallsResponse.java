package com.AlTaraf.Booking.payload.response.Unit;

import com.AlTaraf.Booking.dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
// Event Halls
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventHallsResponse {
    private Long unitId;
    private String nameUnit;
    private String description;
    private int capacityHalls;
    private CityDto cityDto;
    private RegionDto regionDto;
    private Set<FeatureForHallsDto> featuresHallsDto;
    private Set<AvailablePeriodsDto> availablePeriodsHallsDto;
    private int oldPriceHall;
    private int newPriceHall;
    private Double latForMapping;
    private Double longForMapping;
}
