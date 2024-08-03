package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.Calender.ReserveDateRoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateRoomDetailsRepository extends JpaRepository<ReserveDateRoomDetails, Long> {
    @Query("SELECT rd FROM ReserveDateRoomDetails rd WHERE rd.roomDetails.id = :roomDetailsId AND rd.unit.id = :unitId")
    List<ReserveDateRoomDetails> findByRoomDetailsIdAndUnitId(@Param("roomDetailsId") Long roomDetailsId, @Param("unitId") Long unitId);
}
