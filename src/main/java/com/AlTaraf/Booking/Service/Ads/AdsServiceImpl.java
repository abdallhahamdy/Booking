package com.AlTaraf.Booking.Service.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Ads.SliderMapper;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    @Autowired
    private PackageAdsRepository packageAdsRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private SliderMapper sliderMapper;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId) {
        List<Ads> adsList = adsRepository.findByStatusUnitId(statusUnitId); // Fetch ads by statusUnitId
        return sliderMapper.toSliderDtoList(adsList); // Map Ads entities to adsForSliderResponseDto objects    }
    }

    public List<PackageAds> getAllPackageAds() {
        return packageAdsRepository.findAll();
    }

    @Override
    public Ads createAds(Ads ads) {
        try {
            return adsRepository.save(ads);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Ads> getAdsForUserAndStatus(Long userId, String statusUnitName) {
        // Retrieve a List of Ads for the given USER_ID and StatusUnit name
        return adsRepository.findAllAdsByUserIdAndStatusUnitName(userId, statusUnitName);
    }

    @Override
    public void deleteAds(Long id) {
        adsRepository.deleteById(id);
    }

    @Override
    public List<adsForSliderResponseDto> getAdsByAcceptedStatus() {
        List<Ads> adsList = adsRepository.findByStatusUnitName("ACCEPTED");
        return sliderMapper.toSliderDtoList(adsList);
    }

    @Override
    public void updateStatusForAds(Long adsId, Long statusUnitId) {
        Ads ads = adsRepository.findById(adsId)
                .orElseThrow(() -> new EntityNotFoundException("Ads not found with id: " + adsId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        ads.setStatusUnit(statusUnit);
        adsRepository.save(ads);
    }
}
