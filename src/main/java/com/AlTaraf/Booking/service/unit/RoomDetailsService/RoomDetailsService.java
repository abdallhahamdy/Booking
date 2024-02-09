package com.AlTaraf.Booking.service.unit.RoomDetailsService;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;

public interface RoomDetailsService {
    void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetails roomDetails);
    RoomDetails getRoomDetailsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);
}
