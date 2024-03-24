package com.AlTaraf.Booking.Payload.request.ReserveDate;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHotelDto;
import com.AlTaraf.Booking.Entity.Calender.Hotel.DateInfoHotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateHotelDto {
    private List<DateInfoHotelDto> datesInfoHotels;
    private Long roomDetailsId;
    private Long unitId;
    private Long accommodationTypeId;
    private Long unitTypeId;
}
