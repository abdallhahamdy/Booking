package com.AlTaraf.Booking.repository.Ads;

import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.Ads.PackageAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findByStatusUnitId(Long statusUnitId);
}
