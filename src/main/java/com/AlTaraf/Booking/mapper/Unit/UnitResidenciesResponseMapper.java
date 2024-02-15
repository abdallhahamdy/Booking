package com.AlTaraf.Booking.mapper.Unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.payload.response.Unit.UnitResidenciesResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnitResidenciesResponseMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "unitType.id", target = "unitTypeId")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDto")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "accommodationType", target = "accommodationType")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "hotelClassification", target = "hotelClassification")
    @Mapping(source = "roomAvailableSet", target = "roomAvailables")
    @Mapping(source = "basicFeaturesSet", target = "features")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "foodOptionsSet", target = "foodOptions")
    @Mapping(source = "availableAreaSet", target = "availableAreas")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    UnitResidenciesResponseDto toResponseDto(Unit unit);
}
