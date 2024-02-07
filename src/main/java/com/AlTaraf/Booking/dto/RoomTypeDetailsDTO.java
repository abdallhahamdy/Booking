package com.AlTaraf.Booking.dto;

import com.AlTaraf.Booking.entity.unit.unitType.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeDetailsDTO {
    private Long id;
    private RoomType roomType;
    private int number;
    private double oldPrice;
    private double newPrice;
    private Long unitId;

    // Constructors, getters, and setters
}