package com.AlTaraf.Booking.Service.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;

import java.util.List;

public interface AdsService {
    List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId);
    List<PackageAds> getAllPackageAds();
    void createAds(Ads ads);
}
