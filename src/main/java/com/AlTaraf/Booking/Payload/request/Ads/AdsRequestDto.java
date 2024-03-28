package com.AlTaraf.Booking.Payload.request.Ads;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRequestDto {
    private ImageDataDTO imageDataDTO;

    private Long unitId;
//    private Long unitTypeId;
    private Long userId;
    private PackageAds packageAdsId;
    private StatusUnit statusUnit;
//    private List<ImageData> images;
//    private String unitName;
//    private CityDto cityDto;
//    private RegionDto regionDto;
//    private Long statusId;
}
