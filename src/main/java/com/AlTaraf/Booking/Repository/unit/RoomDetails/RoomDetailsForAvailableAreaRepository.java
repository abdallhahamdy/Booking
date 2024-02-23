package com.AlTaraf.Booking.Repository.unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomDetailsForAvailableAreaRepository extends JpaRepository<RoomDetailsForAvailableArea, Long> {
    @Query("SELECT r FROM RoomDetailsForAvailableArea r WHERE r.unit.id = :unitId AND r.availableArea.id = :availableAreaId")
    RoomDetailsForAvailableArea findByUnitIdAndAvailableAreaId(@Param("unitId") Long unitId, @Param("availableAreaId") Long availableAreaId);

    @Query("SELECT COUNT(r) > 0 FROM RoomDetailsForAvailableArea r WHERE r.roomNumber = 0")
    boolean existsByRoomNumberZero();
}
