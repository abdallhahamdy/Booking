package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsStatusMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "unit.unitType.id", target = "unitTypeId")
    @Mapping(source = "fileForAds", target = "fileForAdsDTOS")
    @Mapping(source = "unit.nameUnit", target = "unitName")
    @Mapping(source = "unit.city", target = "cityDto")
    @Mapping(source = "unit.region", target = "regionDto")
    @Mapping(source = "user.packageAds", target = "packageAds")
    @Mapping(source = "statusUnit", target = "statusUnit")
    AdsResponseStatusDto toDto(Ads ads);

    List<AdsResponseStatusDto> toAdsDtoList(List<Ads> ads);

//    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
//    @Mapping(source = "packageAdsId", target = "packageAds.id")
    Ads toEntity(AdsRequestDto adsDto);
}
