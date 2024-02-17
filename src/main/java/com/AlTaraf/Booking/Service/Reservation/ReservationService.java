package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;

public interface ReservationService {
    Reservations saveReservation(Reservations reservations);

    Reservations getReservationById(Long id); // Add this method to get a reservation by ID

}
