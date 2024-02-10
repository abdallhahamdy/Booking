package com.AlTaraf.Booking.repository.Ads;

import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.Ads.PackageAds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsRepository extends JpaRepository<Ads, Long> {
}
