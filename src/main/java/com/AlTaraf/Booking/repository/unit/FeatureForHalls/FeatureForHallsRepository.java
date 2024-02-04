package com.AlTaraf.Booking.repository.unit.FeatureForHalls;

import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureForHallsRepository extends JpaRepository<FeatureForHalls, Long> {
}
