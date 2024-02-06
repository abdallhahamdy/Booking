package com.AlTaraf.Booking.mapper.Unit;

import com.AlTaraf.Booking.dto.Unit.UnitDto;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mappings({
            @Mapping(source = "unit.id", target = "unitId"),
            @Mapping(target = "images", expression = "java(extractFilePaths(unit.getImages()))"),
            @Mapping(source = "unit.nameUnit", target = "nameUnit"),
            @Mapping(source = "unit.city.cityName", target = "cityName"),
            @Mapping(source = "unit.region.regionName", target = "regionName"),
            @Mapping(source = "unit.city.arabicCityName", target = "arabicCityName"),
            @Mapping(source = "unit.region.regionArabicName", target = "regionArabicName")
    })
    UnitDto toUnitDto(Unit unit);

    List<UnitDto> toUnitDtoList(List<Unit> units);

    // Define a method to extract file paths from ImageData entities
    default List<String> extractFilePaths(List<ImageData> images) {
        return images.stream()
                .map(ImageData::getName)
                .collect(Collectors.toList());

    }
}
