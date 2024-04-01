package com.AlTaraf.Booking.Payload.request.Ads;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsResponseDto {
    private Long id;
//    private ImageDataDTO imageDataDTO;
    private Long unitId;
    private Long userId;

    //    private Long unitTypeId;
//    private PackageAds packageAds;
    private List<ImageDataDTO> imageDataDTOs;
//    private String unitName;
//    private CityDtoSample cityDto;
//    private RegionDto regionDto;
//    private StatusUnit statusUnit;
}
