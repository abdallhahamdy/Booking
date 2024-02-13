package com.AlTaraf.Booking.service.Ads;

import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.Ads.PackageAds;
import com.AlTaraf.Booking.mapper.Ads.SliderMapper;
import com.AlTaraf.Booking.payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.repository.Ads.AdsRepository;
import com.AlTaraf.Booking.repository.Ads.PackageAdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService{
    @Autowired
    private PackageAdsRepository packageAdsRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private SliderMapper sliderMapper;

    @Override
    public List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId) {
        List<Ads> adsList = adsRepository.findByStatusUnitId(statusUnitId); // Fetch ads by statusUnitId
        return sliderMapper.toSliderDtoList(adsList); // Map Ads entities to adsForSliderResponseDto objects    }
    }

    public List<PackageAds> getAllPackageAds() {
        return packageAdsRepository.findAll();
    }

    @Override
    public void createAds(Ads ads) {

    }


}
