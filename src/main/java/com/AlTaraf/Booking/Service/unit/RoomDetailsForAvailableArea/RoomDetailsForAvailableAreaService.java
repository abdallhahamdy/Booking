package com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea;

import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;

public interface RoomDetailsForAvailableAreaService {
    void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetailsForAvailableArea roomDetailsForAvailableArea);
    RoomDetailsForAvailableArea getRoomDetailsByUnitIdAndAvailableAreaId(Long unitId, Long availableAreaId);
}
