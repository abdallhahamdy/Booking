package com.AlTaraf.Booking.controller.packageAds;

import com.AlTaraf.Booking.entity.Ads.PackageAds;
import com.AlTaraf.Booking.service.packageAds.PackageAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packageAds")
public class PackageAdsController {

    @Autowired
    private PackageAdsService packageAdsService;

    @GetMapping
    public ResponseEntity<List<PackageAds>> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = packageAdsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
