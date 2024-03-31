package com.AlTaraf.Booking.Controller.Ads;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Ads.AdsMapper;
import com.AlTaraf.Booking.Mapper.Ads.AdsStatusMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseDto;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private AdsStatusMapper adsStatusMapper;

    @GetMapping("/Package-Ads")
    public ResponseEntity<?> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content"));
        }
    }


    @GetMapping("units/byUserId/{userId}")
    public ResponseEntity<?> getUnitsByUserId(@PathVariable Long userId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, PageRequest.of(page, size));

        if (unitsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No units found for user ID: " + userId));
        } else {
            List<UnitDtoFavorite> unitGeneralResponseDtos = unitsPage.getContent().stream()
                    .map(unitFavoriteMapper::toUnitFavoriteDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(unitGeneralResponseDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAds(@RequestBody AdsRequestDto adsRequestDto) {
        try {
            Ads ads = adsService.createAds(adsMapper.toEntity(adsRequestDto));
            return ResponseEntity.status(HttpStatus.CREATED).body("Ads created successfully! with id: " + ads.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ads: " + e.getMessage());
        }
    }

    @GetMapping("/Status-Unit/Ads")
    public ResponseEntity<?> getAdsByUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitName") String statusUnitName) {

        List<Ads> ads = adsService.getAdsForUserAndStatus(userId, statusUnitName);

        if (!ads.isEmpty()) {
            List<AdsResponseDto> adsDtoList = adsStatusMapper.toAdsDtoList(ads);
            return new ResponseEntity<>(adsDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @DeleteMapping("Delete/Ads/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Long id) {

        try {
            adsService.deleteAds(id);
            ApiResponse response = new ApiResponse(200, "Ads deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/Accepted/Status")
    public ResponseEntity<?> getAdsByAcceptedStatus() {
        try {
            List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
            System.out.println("Exception Accepted Ads: " + e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content"));
        }
    }

}
