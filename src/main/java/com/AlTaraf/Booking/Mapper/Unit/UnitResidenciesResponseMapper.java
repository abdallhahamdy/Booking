package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Image.FileForUnitDTO;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Payload.response.Unit.UnitResidenciesResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitResidenciesResponseMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "unitType.id", target = "unitTypeId")
//    @Mapping(target = "imagePaths", expression = "java(extractFilePaths(unit.getFileDownloadUri()))")
    @Mapping(target = "imagePaths", expression = "java(extractFilePaths(unit.getFileForUnits()))")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDto")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "accommodationType", target = "accommodationType")
//    @Mapping(source = "images", target = "images")
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
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    UnitResidenciesResponseDto toResponseDto(Unit unit);

    default List<FileForUnitDTO> mapImages(List<FileForUnit> files) {
        return files.stream()
                .map(this::mapToFileForUnitDTO)
                .collect(Collectors.toList());
    }

    default FileForUnitDTO mapToFileForUnitDTO(FileForUnit fileForUnit) {
        FileForUnitDTO fileForUnitDTO = new FileForUnitDTO();
        fileForUnitDTO.setName(fileForUnit.getName());
        fileForUnitDTO.setFileDownloadUri(fileForUnit.getFileDownloadUri());
        return fileForUnitDTO;
    }

    default List<String> extractFilePaths(List<FileForUnit> images) {
        return images.stream()
                .map(FileForUnit::getFileDownloadUri)
                .collect(Collectors.toList());
    }
}
