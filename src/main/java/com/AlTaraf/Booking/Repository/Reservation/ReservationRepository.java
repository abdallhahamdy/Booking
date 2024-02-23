package com.AlTaraf.Booking.Repository.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {

    List<Reservations> findAllReservationsByUserIdAndStatusUnitName(Long userId, String statusUnitName);

    @Query("SELECT r FROM Reservations r WHERE r.unit.id = :unitId")
    List<Reservations> findByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT r.unit FROM Reservations r WHERE r.id = :reservationId")
    Unit findUnitByReservationId(@Param("reservationId") Long reservationId);
}
