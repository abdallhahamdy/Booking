package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveDateRepository extends JpaRepository<ReserveDate, Long> {

    @Query("SELECT rd FROM ReserveDate rd WHERE rd.roomDetailsForAvailableArea.id = :roomDetailsForAvailableAreaId AND rd.roomDetailsForAvailableArea.unit.id = :unitId")
    List<ReserveDate> findByRoomDetailsForAvailableAreaIdAndUnitId(@Param("roomDetailsForAvailableAreaId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);



}
