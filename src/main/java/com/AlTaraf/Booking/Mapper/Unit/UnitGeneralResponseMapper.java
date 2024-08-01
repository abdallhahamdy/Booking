package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Image.FileForUnitDTO;
import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Payload.response.Unit.UnitGeneralResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitGeneralResponseMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "unitType", target = "unitType")
    @Mapping(source = "user.deviceToken", target = "deviceToken")
    @Mapping(target = "imagePaths", expression = "java(extractFilePaths(unit.getFileForUnits()))")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDtoSample")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "accommodationType", target = "accommodationType")
    @Mapping(source = "typesOfEventHalls", target = "typesOfEventHalls")
//    @Mapping(source = "images", target = "images.id")
//    @Mapping(source = "images.name", target = "images.name")
//    @Mapping(source = "images.imagePath", target = "images.imagePath")
    @Mapping(source = "hotelClassification", target = "hotelClassification")
    @Mapping(source = "typeOfApartment", target = "typeOfApartment")
    @Mapping(source = "roomAvailableSet", target = "roomAvailables")
    @Mapping(source = "basicFeaturesSet", target = "features")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "availableAreaSet", target = "availableAreas")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "featuresHallsSet", target = "featuresHallsDto", qualifiedByName = "mapEntityToFeaturesHallsDto")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHallsDto", qualifiedByName = "mapEntityToAvailablePeriodsHallsDto")
    @Mapping(source = "roomDetailsForAvailableAreaList", target = "roomDetailsForAvailableAreaList")
    @Mapping(source = "roomDetails", target = "roomDetails")
    @Mapping(source = "oldPriceHall", target = "oldPriceHall")
    @Mapping(source = "newPriceHall", target = "newPriceHall")
    @Mapping(source = "chaletOldPrice", target = "chaletOldPrice")
    @Mapping(source = "chaletNewPrice", target = "chaletNewPrice")
    @Mapping(source = "apartmentOldPrice", target = "apartmentOldPrice")
    @Mapping(source = "apartmentNewPrice", target = "apartmentNewPrice")
    @Mapping(source = "loungeOldPrice", target = "loungeOldPrice")
    @Mapping(source = "loungeNewPrice", target = "loungeNewPrice")
    @Mapping(source = "evaluation.name", target = "evaluationName")
    @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName")
    @Mapping(source = "price", target = "price", defaultValue = "0")
    @Mapping(source = "commission", target = "commission")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    UnitGeneralResponseDto toResponseDto(Unit unit);

    @Named("mapEntityToFeaturesHallsDto")
    default Set<FeatureForHallsDto> mapEntityToFeaturesHallsDto(Set<FeatureForHalls> featuresHallsSet) {
        return featuresHallsSet.stream()
                .map(feature -> new FeatureForHallsDto(feature.getId(), feature.getName(), feature.getArabicName())) // Assuming FeatureForHallsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    @Named("mapEntityToAvailablePeriodsHallsDto")
    default Set<AvailablePeriodsDto> mapEntityToAvailablePeriodsHallsDto(Set<AvailablePeriods> availablePeriodsHallsSet) {
        return availablePeriodsHallsSet.stream()
                .map(period -> new AvailablePeriodsDto(period.getId(), period.getName(), period.getArabicName())) // Assuming AvailablePeriodsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    default List<FileForUnitDTO> mapImages(List<FileForUnit> images) {
        return images.stream()
                .map(this::mapToImageDataDTO)
                .collect(Collectors.toList());
    }

    default FileForUnitDTO mapToImageDataDTO(FileForUnit fileForUnit) {
        FileForUnitDTO imageDataDTO = new FileForUnitDTO();
        imageDataDTO.setName(fileForUnit.getName());
        imageDataDTO.setFileDownloadUri(fileForUnit.getFileDownloadUri());
        return imageDataDTO;
    }

    default List<String> extractFilePaths(List<FileForUnit> fileForUnits) {
        return fileForUnits.stream()
                .map(FileForUnit::getFileDownloadUri)
                .collect(Collectors.toList());
    }

}
