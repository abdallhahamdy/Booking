package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Payload.request.Ads.AdsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsStatusMapper {
    @Mapping(source = "id", target = "id")
    AdsDto toDto(Ads ads);

    List<AdsDto> toAdsDtoList(List<Ads> ads);

    @Mapping(source = "id", target = "id")
    Ads toEntity(AdsDto adsDto);
}
