package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateHallsRepository extends JpaRepository<ReserveDateHalls, Long> {

    @Query("SELECT rd FROM ReserveDateHalls rd WHERE rd.unit.id = :unitId AND rd.reserve = true")
    List<ReserveDateHalls> findByUnitIdAndReserveIsTrue(@Param("unitId") Long unitId);

    @Query("SELECT rd FROM ReserveDateHalls rd WHERE rd.unit.id = :unitId")
    List<ReserveDateHalls> findByUnitId(@Param("unitId") Long unitId);
}
