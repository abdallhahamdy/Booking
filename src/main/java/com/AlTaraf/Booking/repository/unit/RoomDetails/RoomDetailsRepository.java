package com.AlTaraf.Booking.repository.unit.RoomDetails;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
    RoomDetails findByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

}
