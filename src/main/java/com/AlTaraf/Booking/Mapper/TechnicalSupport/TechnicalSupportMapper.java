package com.AlTaraf.Booking.Mapper.TechnicalSupport;

import com.AlTaraf.Booking.Dto.TechnicalSupport.TechnicalSupportDTO;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TechnicalSupportMapper {

    TechnicalSupportMapper INSTANCE = Mappers.getMapper(TechnicalSupportMapper.class);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    TechnicalSupport toEntity(TechnicalSupportDTO technicalSupportDTO);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "name", source = "name")
    TechnicalSupportDTO toDto(TechnicalSupport technicalSupport);

    TechnicalSupportDTO toDTO(TechnicalSupport technicalSupport);
}