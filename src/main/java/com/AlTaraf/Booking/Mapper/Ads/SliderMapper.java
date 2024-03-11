package com.AlTaraf.Booking.Mapper.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageDataForAds;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SliderMapper {

    @Mappings({
            @Mapping(source = "ads.id", target = "adsId"),
            @Mapping(target = "imagePath", expression = "java(extractImagePath(ads.getImages()))"),
//            @Mapping(source = "ads.unit", target = "unitDtoFavorite"),
            @Mapping(source = "ads.unit.id", target = "unitId"),
            @Mapping(source = "ads.unit.nameUnit", target = "nameUnit"),
            @Mapping(source = "ads.unit.city", target = "city"),
            @Mapping(source = "ads.unit.region", target = "region"),
            // You need to provide a way to get UnitDtoFavorite from Ads or set it manually
    })
    adsForSliderResponseDto toSliderDto(Ads ads);

    List<adsForSliderResponseDto> toSliderDtoList(List<Ads> adsList);

    // Define a method to extract the first image path from the list of ImageData entities
    default String extractImagePath(List<ImageDataForAds> images) {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getImagePath();
        }
        return null; // or provide a default image path
    }
}
