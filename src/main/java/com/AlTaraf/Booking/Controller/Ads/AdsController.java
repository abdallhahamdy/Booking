package com.AlTaraf.Booking.Controller.Ads;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Ads.AdsMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.unit.UnitService;
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
    private UnitFavoriteMapper unitFavoriteMapper;

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
            // Create an instance of UnitFavoriteMapper and use it to call the method
            List<UnitDtoFavorite> unitGeneralResponseDtos = units.stream()
                    .map(unitFavoriteMapper::toUnitFavoriteDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(unitGeneralResponseDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAds(@RequestBody AdsRequestDto adsDto) {
        try {
            Ads ads = adsMapper.toEntity(adsDto);
            adsService.createAds(ads);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ads created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ads: " + e.getMessage());
        }
    }


//    @GetMapping("/Accepted/Status")
//    public ResponseEntity<List<adsForSliderResponseDto>> getAdsByAcceptedStatus() {
//        List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
//        return ResponseEntity.ok(ads);
//    }

}
