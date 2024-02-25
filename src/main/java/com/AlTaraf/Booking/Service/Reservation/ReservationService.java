package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationService {
    Reservations saveReservation(Reservations reservations);

    Reservations getReservationById(Long id); // Add this method to get a reservation by ID

    List<Reservations> getReservationsForUserAndStatus(Long userId, String statusUnitName);

    List<Reservations> findReservationByUnitId(Long unitId);

    Unit findUnitByReservationId(Long reservationId);

    void updateStatusForReservation(Long reservationId, Long statusUnitId);

    AvailableArea getAvailableAreaByReservations(Long reservationId);

    List<Reservations> getReservationForUserAndStatus(Long userId, String statusUnitName);

    void deleteUnit(Long id);
}