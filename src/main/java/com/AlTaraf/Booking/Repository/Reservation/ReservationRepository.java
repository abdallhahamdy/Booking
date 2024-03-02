package com.AlTaraf.Booking.Repository.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT r.availableArea FROM Reservations r WHERE r.id = :reservationId")
    AvailableArea findAvailableAreaIdByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM Reservations r JOIN r.user u JOIN r.statusUnit s WHERE u.id = :userId AND s.name = :statusUnitName")
    Page<Reservations> findByUserIdAndStatusUnitName(@Param("userId") Long userId, @Param("statusUnitName") String statusUnitName, Pageable pageable);

    @Query("SELECT r FROM Reservations r WHERE r.unit.id = :unitId")
    Page<Reservations> findByUnitId(Long unitId, Pageable pageable);

    @Query("SELECT r FROM Reservations r JOIN r.statusUnit s WHERE s.name = :statusName AND r.unit.id = :unitId")
    Page<Reservations> findByStatusNameAndUnitId(@Param("statusName") String statusName, @Param("unitId") Long unitId, Pageable pageable);
}
