package com.AlTaraf.Booking.repository.unit.statusUnit;

import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUnitRepository extends JpaRepository<StatusUnit, Long> {

}
