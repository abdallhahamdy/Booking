package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Entity.Image.ImageData;
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
    @Mapping(target = "imagePaths", expression = "java(extractFilePaths(unit.getImages()))")
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

    default List<ImageDataDTO> mapImages(List<ImageData> images) {
        return images.stream()
                .map(this::mapToImageDataDTO)
                .collect(Collectors.toList());
    }

    default ImageDataDTO mapToImageDataDTO(ImageData imageData) {
        ImageDataDTO imageDataDTO = new ImageDataDTO();
        imageDataDTO.setId(imageData.getId());
        imageDataDTO.setName(imageData.getName());
        imageDataDTO.setImagePath(imageData.getImagePath());
        return imageDataDTO;
    }

    default List<String> extractFilePaths(List<ImageData> images) {
        return images.stream()
                .map(ImageData::getImagePath)
                .collect(Collectors.toList());
    }
}
