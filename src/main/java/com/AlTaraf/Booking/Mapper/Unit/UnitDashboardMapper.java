package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UnitDashboardMapper {
    @Mappings({
            @Mapping(source = "unit.user.username", target = "traderName"),
            @Mapping(source = "unit.user.phone", target = "traderPhone"),
            @Mapping(source = "unit.region", target = "regionDto"),
            @Mapping(source = "unit.city", target = "cityDtoSample")
    })
    UnitDashboard toUnitDashboard(Unit unit);

    }

