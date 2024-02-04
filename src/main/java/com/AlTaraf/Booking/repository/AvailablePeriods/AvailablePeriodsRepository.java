package com.AlTaraf.Booking.repository.AvailablePeriods;

import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailablePeriodsRepository  extends JpaRepository<AvailablePeriods, Long> {
}
