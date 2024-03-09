package com.AlTaraf.Booking.Dto.Unit;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitDashboard {
    private String traderName;
    private String traderPhone;
    private RegionDto regionDto;
    private CityDtoSample cityDtoSample;
}
