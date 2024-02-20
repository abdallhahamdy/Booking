package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;

import java.util.List;

public interface ReservationService {
    Reservations saveReservation(Reservations reservations);

    Reservations getReservationById(Long id); // Add this method to get a reservation by ID

    List<Reservations> getReservationsForUserAndStatus(Long userId, String statusUnitName);

}
