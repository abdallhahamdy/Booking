package com.AlTaraf.Booking.Dto.calender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateHallsDto {
    private List<DateInfoHallsDto> dateInfoList;
    private Long unitId;
}