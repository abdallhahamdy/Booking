package com.AlTaraf.Booking.mapper.Image;

import com.AlTaraf.Booking.dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.entity.Image.ImageData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageDataMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "imagePath", target = "imagePath")
    ImageDataDTO toDTO(ImageData imageData);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "imagePath", target = "imagePath")
    ImageData toEntity(ImageDataDTO imageDataDTO);
}