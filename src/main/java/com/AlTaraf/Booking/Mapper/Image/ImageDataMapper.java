package com.AlTaraf.Booking.Mapper.Image;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Entity.Image.ImageData;
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