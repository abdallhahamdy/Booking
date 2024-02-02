package com.AlTaraf.Booking.repository.unit.subFeature;

import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubFeatureRepository extends JpaRepository<SubFeature, Long> {

}
