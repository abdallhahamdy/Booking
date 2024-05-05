package com.AlTaraf.Booking.Payload.request.Ads;

import com.AlTaraf.Booking.Dto.Image.FileForAdsDTO;
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
    private List<FileForAdsDTO> fileForAdsDTOS;
//    private String unitName;
//    private CityDtoSample cityDto;
//    private RegionDto regionDto;
//    private StatusUnit statusUnit;
}
