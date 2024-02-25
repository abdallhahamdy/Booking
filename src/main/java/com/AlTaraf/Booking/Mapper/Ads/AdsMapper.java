package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Payload.request.Ads.AdsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "packageAds.id", target = "packageAdsId")
//    @Mapping(source = "statusUnit.id", target = "statusId")
    AdsDto toDto(Ads ads);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "packageAdsId", target = "packageAds.id")
//    @Mapping(source = "statusId", target = "statusUnit.id")
    Ads toEntity(AdsDto adsDto);
}