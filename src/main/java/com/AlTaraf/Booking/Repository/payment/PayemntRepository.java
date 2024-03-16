package com.AlTaraf.Booking.Repository.payment;

import com.AlTaraf.Booking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PayemntRepository extends JpaRepository<Payment, Long> {


}
