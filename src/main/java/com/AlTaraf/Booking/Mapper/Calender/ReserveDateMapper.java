package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import com.AlTaraf.Booking.Entity.Calender.DateInfo;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReserveDateMapper {

    ReserveDateMapper INSTANCE = Mappers.getMapper(ReserveDateMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetailsForAvailableAreaId", target = "roomDetailsForAvailableArea.id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "accommodationTypeId", target = "unit.accommodationType.id")
    @Mapping(source = "unitTypeId", target = "unit.unitType.id")
    ReserveDate reserveDateRequestToReserveDate(ReserveDateDto reserveDateRequest);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetailsForAvailableArea.id", target = "roomDetailsForAvailableAreaId")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateDto reserveDateToReserveDateRequest(ReserveDate reserveDate);

    @Named("toDateList")
    default List<Date> toDateList(List<Date> dates) {
        return dates.stream()
                .map(date -> new Date(date.getTime()))
                .collect(Collectors.toList());
    }

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfo> mapDateInfoDtoList(List<DateInfoDto> dateInfoList);


    default RoomDetailsForAvailableArea mapRoomDetailsForAvailableArea(Long roomDetailsForAvailableAreaId) {
        return roomDetailsForAvailableAreaId != null ? new RoomDetailsForAvailableArea(roomDetailsForAvailableAreaId) : null;
    }
}
