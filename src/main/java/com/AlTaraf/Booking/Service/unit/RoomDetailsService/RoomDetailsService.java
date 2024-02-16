package com.AlTaraf.Booking.Service.unit.RoomDetailsService;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;

public interface RoomDetailsService {
    void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetails roomDetails);
    RoomDetails getRoomDetailsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);
}
