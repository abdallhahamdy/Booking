package com.AlTaraf.Booking.Payload.request.Ads;

import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRequestDto {

    private Long unitId;
//    private List<ImageDataDTO> imageDataDTOs;
    //    private Long unitTypeId;
    private Long userId;
    private Long packageAdsId;
    private Long statusUnitId;
//    private List<ImageData> images;
//    private String unitName;
//    private CityDto cityDto;
//    private RegionDto regionDto;
//    private Long statusId;
}
