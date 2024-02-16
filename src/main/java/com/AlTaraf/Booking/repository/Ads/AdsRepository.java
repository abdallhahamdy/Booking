package com.AlTaraf.Booking.Repository.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findByStatusUnitId(Long statusUnitId);
}
