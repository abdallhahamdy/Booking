package com.AlTaraf.Booking.mapper.TechnicalSupport;

import com.AlTaraf.Booking.dto.TechnicalSupport.TechnicalSupportDTO;
import com.AlTaraf.Booking.entity.TechnicalSupport.TechnicalSupport;
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

    TechnicalSupportDTO toDTO(TechnicalSupport technicalSupport);
}