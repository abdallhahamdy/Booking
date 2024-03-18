package com.AlTaraf.Booking.Dto.Unit;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitDashboard {
    private Long unitId;
    private String traderName;
    private String traderPhone;
    private RegionDto regionDto;
    private CityDtoSample cityDtoSample;
    private StatusUnit statusUnit;
}
