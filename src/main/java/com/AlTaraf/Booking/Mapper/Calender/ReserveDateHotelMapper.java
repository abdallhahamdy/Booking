package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHotelDto;
import com.AlTaraf.Booking.Entity.Calender.DateInfo;
import com.AlTaraf.Booking.Entity.Calender.Hotel.DateInfoHotel;
import com.AlTaraf.Booking.Entity.Calender.Hotel.ReserveDateHotel;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateHotelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReserveDateHotelMapper {

    ReserveDateHotelMapper INSTANCE = Mappers.getMapper(ReserveDateHotelMapper.class);

    @Mapping(source = "datesInfoHotels", target = "datesInfoHotels")
    @Mapping(source = "roomDetailsId", target = "roomDetails.id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "accommodationTypeId", target = "unit.accommodationType.id")
    @Mapping(source = "unitTypeId", target = "unit.unitType.id")
    ReserveDateHotel reserveDateRequestToReserveDateHotel(ReserveDateHotelDto reserveDateHotelDto);

    @Mapping(source = "datesInfoHotels", target = "datesInfoHotels")
    @Mapping(source = "roomDetails.id", target = "roomDetailsId")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateHotelDto reserveDateHotelToReserveDateHotelRequest(ReserveDateHotel reserveDate);

    @Mapping(source = "datesInfoHotels", target = "datesInfoHotels")
    List<DateInfoHotel> mapDateInfoDtoList(List<DateInfoHotelDto> dateInfoHotelDtoList);

    @Named("toDateList")
    default List<Date> toDateList(List<Date> dates) {
        return dates.stream()
                .map(date -> new Date(date.getTime()))
                .collect(Collectors.toList());
    }


}
