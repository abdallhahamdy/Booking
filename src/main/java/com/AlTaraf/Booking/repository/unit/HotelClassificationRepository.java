package com.AlTaraf.Booking.repository.unit;

import com.AlTaraf.Booking.entity.unit.HotelClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelClassificationRepository extends JpaRepository<HotelClassification, Long> {

}
