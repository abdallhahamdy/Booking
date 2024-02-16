package com.AlTaraf.Booking.Repository.Reservation;

import com.AlTaraf.Booking.Entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {


}
