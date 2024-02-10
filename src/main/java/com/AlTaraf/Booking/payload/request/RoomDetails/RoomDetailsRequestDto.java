package com.AlTaraf.Booking.payload.request.RoomDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsRequestDto {
    private Long id;
    private Long roomAvailableId;
    private int roomNumber;
    private BigDecimal newPrice;
    private BigDecimal oldPrice;
}
