package com.AlTaraf.Booking.repository.Ads;

import com.AlTaraf.Booking.entity.Ads.PackageAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageAdsRepository extends JpaRepository<PackageAds, Long> {
    // You can add custom query methods here if needed
}