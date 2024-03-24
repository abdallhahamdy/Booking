package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHallsDto;
import com.AlTaraf.Booking.Dto.calender.ReserveDateHallsDto;
import com.AlTaraf.Booking.Entity.Calender.Halls.DateInfoHalls;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveDateHallsMapper {
    ReserveDateHallsMapper INSTANCE = Mappers.getMapper(ReserveDateHallsMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.accommodationType.id", target = "accommodationTypeId")
    @Mapping(source = "unit.unitType.id", target = "unitTypeId")
    ReserveDateHallsDto toDto(ReserveDateHalls reserveDateHalls);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "accommodationTypeId", target = "unit.accommodationType.id")
    @Mapping(source = "unitTypeId", target = "unit.unitType.id")
    ReserveDateHalls toEntity(ReserveDateHallsDto reserveDateHallsDto);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHallsDto> mapDateInfoList(List<DateInfoHalls> dateInfoList);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHalls> mapDateInfoDtoList(List<DateInfoHallsDto> dateInfoList);

    default String mapDate(Date date) {
        // Implement custom logic to convert Date to String
        return date.toString(); // For example
    }

    default Date mapDateDto(String date) {
        // Implement custom logic to convert String to Date
        return new Date(); // For example
    }
}
