package com.AlTaraf.Booking.Payload.request.ReserveDate;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateDto {
    private List<DateInfoDto> dateInfoList;
    private Long roomDetailsForAvailableAreaId;
    private Long unitId;
//    private Long accommodationTypeId;
//    private Long unitTypeId;
}
