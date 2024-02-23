package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitFavoriteMapper {

    @Mappings({
            @Mapping(source = "unit.id", target = "unitId"),
            @Mapping(source = "unitType.id", target = "unitTypeId"),
            @Mapping(target = "imagePaths", expression = "java(extractFilePaths(unit.getImages()))"),
            @Mapping(source = "unit.nameUnit", target = "nameUnit"),
            @Mapping(source = "unit.city.cityName", target = "cityName"),
            @Mapping(source = "unit.region.regionName", target = "regionName"),
            @Mapping(source = "unit.city.arabicCityName", target = "arabicCityName"),
            @Mapping(source = "unit.region.regionArabicName", target = "regionArabicName"),
            @Mapping(source = "unit.favorite", target = "favorite"),
            @Mapping(source = "latForMapping", target = "latForMapping"),
            @Mapping(source = "longForMapping", target = "longForMapping"),
            @Mapping(source = "price", target = "price", defaultValue = "0"),
            @Mapping(source = "evaluation.id", target = "evaluationId")
    })
     UnitDtoFavorite toUnitFavoriteDto(Unit unit);

    List<UnitDtoFavorite> toUnitFavoriteDtoList(List<Unit> units);

    // Define a method to extract file paths from ImageData entities

    default List<String> extractFilePaths(List<ImageData> images) {
        return images.stream()
                .map(ImageData::getImagePath)
                .collect(Collectors.toList());
    }

}
