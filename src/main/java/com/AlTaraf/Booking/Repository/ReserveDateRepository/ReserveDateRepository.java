package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateRepository extends JpaRepository<ReserveDate, Long> {

    @Query("SELECT rd FROM ReserveDate rd WHERE rd.roomDetailsForAvailableArea.id = :roomDetailsForAvailableAreaId AND rd.unit.id = :unitId")
    List<ReserveDate> findByRoomDetailsForAvailableAreaIdAndUnitId(@Param("roomDetailsForAvailableAreaId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);


//    @Query("SELECT rd FROM ReserveDate rd WHERE rd.roomDetailsForAvailableArea.id = :roomDetailsForAvailableAreaId AND rd.unit.id = :unitId AND rd.reserve = true")
//    List<ReserveDate> findByRoomDetailsForAvailableAreaIdAndUnitIdAndReserveTrue(@Param("roomDetailsForAvailableAreaId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);

}
