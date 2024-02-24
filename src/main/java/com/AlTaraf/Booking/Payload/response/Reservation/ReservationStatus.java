package com.AlTaraf.Booking.Payload.response.Reservation;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationStatus {
    private Long reservationId;
    private Long unitId;
    private String unitName;
    private CityDto cityDto;
    private RegionDto regionDto;
    private int price;
}
