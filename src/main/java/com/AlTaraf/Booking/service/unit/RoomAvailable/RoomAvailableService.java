package com.AlTaraf.Booking.service.unit.RoomAvailable;

import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;

import java.util.List;

public interface RoomAvailableService {
    List<RoomAvailable> getAllRoomAvailable();

    List<RoomAvailable> getRoomAvailableByIds(List<Long> roomAvailableIds);

}
