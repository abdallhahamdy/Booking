package com.AlTaraf.Booking.Service.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;

import java.util.List;

public interface AdsService {
    List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId);
    List<PackageAds> getAllPackageAds();
    Ads createAds(Ads ads);

    List<Ads> getAdsForUserAndStatus(Long userId, String statusUnitName);

    void deleteAds(Long id);

    List<adsForSliderResponseDto> getAdsByAcceptedStatus();

    void updateStatusForAds(Long adsId, Long statusUnitId);

    List<Ads> findByUnitId(Long unitId);
}
