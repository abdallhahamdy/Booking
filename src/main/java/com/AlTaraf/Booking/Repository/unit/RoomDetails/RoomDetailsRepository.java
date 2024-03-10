package com.AlTaraf.Booking.Repository.unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
    RoomDetails findByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

    boolean existsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

}
