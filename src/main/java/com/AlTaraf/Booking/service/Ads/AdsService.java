package com.AlTaraf.Booking.service.Ads;

import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.Ads.PackageAds;
import com.AlTaraf.Booking.payload.response.Ads.adsForSliderResponseDto;

import java.util.List;

public interface AdsService {
    List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId);
    List<PackageAds> getAllPackageAds();
    void createAds(Ads ads);
}
