package com.AlTaraf.Booking.Payload.request.ReserveDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateDto {
    private List<Date> dateList;
    private Long roomDetailsForAvailableAreaId;
    private Long unitId;
}
