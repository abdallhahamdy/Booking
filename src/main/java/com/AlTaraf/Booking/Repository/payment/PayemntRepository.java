package com.AlTaraf.Booking.Repository.payment;

import com.AlTaraf.Booking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PayemntRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.custom_ref = :customRef")
    Payment findByCustomRef(@Param("customRef") String customRef);


}
