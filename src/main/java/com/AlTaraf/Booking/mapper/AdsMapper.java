package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.dto.AdsDto;
import com.AlTaraf.Booking.entity.Ads.Ads;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "unit.id", target = "unitId")
    AdsDto toDto(Ads ads);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "unitId", target = "unit.id")
    Ads toEntity(AdsDto adsDto);
}
