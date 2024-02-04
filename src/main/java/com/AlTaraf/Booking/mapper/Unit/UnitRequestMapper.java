package com.AlTaraf.Booking.mapper.Unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitRequestMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitTypeId", target = "unitType.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "regionId", target = "region.id")
    @Mapping(source = "hotelClassificationId", target = "hotelClassification.id")
    Unit toUnit(UnitRequestDto unitRequestDto);

    List<Unit> toUnitList(List<UnitRequestDto> unitRequestDtos);
}
