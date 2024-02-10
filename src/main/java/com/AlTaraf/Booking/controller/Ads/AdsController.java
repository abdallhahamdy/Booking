package com.AlTaraf.Booking.controller.Ads;

import com.AlTaraf.Booking.dto.AdsDto;
import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.Ads.PackageAds;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.mapper.AdsMapper;
import com.AlTaraf.Booking.mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.payload.response.Unit.UnitGeneralResponseDto;
import com.AlTaraf.Booking.service.packageAds.AdsService;
import com.AlTaraf.Booking.service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Ads")
public class AdsController {

    @Autowired
    private AdsService adsService;

    @Autowired
    private UnitGeneralResponseMapper unitGeneralResponseMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AdsMapper adsMapper;

    @GetMapping("/Package-Ads")
    public ResponseEntity<List<PackageAds>> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("units/byUserId/{userId}")
    public ResponseEntity<?> getUnitsByUserId(@PathVariable Long userId) {
        List<Unit> units = unitService.getUnitsByUserId(userId);

        if (units.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No units found for user ID: " + userId));
        } else {
            List<UnitGeneralResponseDto> unitGeneralResponseDtos = units.stream()
                    .map(unitGeneralResponseMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(unitGeneralResponseDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAds(@RequestBody AdsDto adsDto) {
        try {
            Ads ads = adsMapper.toEntity(adsDto);
            adsService.createAds(ads);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ads created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ads: " + e.getMessage());
        }
    }
}
