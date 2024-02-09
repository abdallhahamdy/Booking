package com.AlTaraf.Booking.payload.response.RoomDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsResponseDto {
    private int roomNumber;
    private BigDecimal newPrice;
    private BigDecimal oldPrice;
}
