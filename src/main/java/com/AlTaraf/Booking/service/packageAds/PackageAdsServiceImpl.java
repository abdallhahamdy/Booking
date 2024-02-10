package com.AlTaraf.Booking.service.packageAds;

import com.AlTaraf.Booking.entity.Ads.PackageAds;
import com.AlTaraf.Booking.repository.Ads.PackageAdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageAdsServiceImpl implements PackageAdsService {

    @Autowired
    private PackageAdsRepository packageAdsRepository;

    public List<PackageAds> getAllPackageAds() {
        return packageAdsRepository.findAll();
    }

}
