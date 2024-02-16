package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "packageAds.id", target = "packageAdsId")
    @Mapping(source = "statusUnit.id", target = "statusId")
    AdsRequestDto toDto(Ads ads);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "packageAdsId", target = "packageAds.id")
    @Mapping(source = "statusId", target = "statusUnit.id")
    Ads toEntity(AdsRequestDto adsDto);
}
