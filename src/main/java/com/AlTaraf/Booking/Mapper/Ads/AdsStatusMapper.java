package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Payload.request.Ads.AdsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsStatusMapper {
//    @Mapping(source = "id", target = "id")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.unitType.id", target = "unitTypeId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "packageAds.id", target = "packageAdsId")
    AdsDto toDto(Ads ads);

    List<AdsDto> toAdsDtoList(List<Ads> ads);

//    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "packageAdsId", target = "packageAds.id")
    Ads toEntity(AdsDto adsDto);
}
