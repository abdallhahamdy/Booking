package com.AlTaraf.Booking.repository.unit.availableArea;

import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvailableAreaRepository extends JpaRepository<AvailableArea, Long> {

}
