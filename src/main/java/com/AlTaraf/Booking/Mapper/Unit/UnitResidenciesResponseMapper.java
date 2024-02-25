package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Payload.response.Unit.UnitResidenciesResponseDto;
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
    @Mapping(source = "evaluation.name", target = "evaluationName")
    @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName")
    UnitResidenciesResponseDto toResponseDto(Unit unit);
}