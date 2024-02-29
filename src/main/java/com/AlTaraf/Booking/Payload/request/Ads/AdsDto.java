package com.AlTaraf.Booking.Payload.request.Ads;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {
    private Long id;
    private Long unitId;
    private Long unitTypeId;
    private Long userId;
    private Long packageAdsId;
    private List<ImageData> images;
    private String unitName;
    private CityDto cityDto;
    private RegionDto regionDto;
//    private Long statusId;
}
