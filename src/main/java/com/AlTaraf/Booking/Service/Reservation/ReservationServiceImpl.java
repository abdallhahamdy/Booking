package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Reservations saveReservation(Reservations reservations) {
        try {
            return reservationRepository.save(reservations);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }
}

