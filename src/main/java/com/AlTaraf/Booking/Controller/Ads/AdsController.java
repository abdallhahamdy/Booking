package com.AlTaraf.Booking.Controller.Ads;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Mapper.Ads.AdsMapper;
import com.AlTaraf.Booking.Mapper.Ads.AdsStatusMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.Payload.request.Ads.AdsRequestDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseDto;
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseStatusDto;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.ApiResponseFlag;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.unit.statusUnit.StatusUnitService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    StatusRepository statusUnitRepository;

    @GetMapping("/Package-Ads")
    public ResponseEntity<?> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No_content.message"));
        }
    }


    @GetMapping("units/byUserId/{userId}")
    public ResponseEntity<?> getUnitsByUserId(@PathVariable Long userId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, PageRequest.of(page, size));

        if (unitsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No_unit_for_user.message:  " + userId));
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Ads_created.message " + ads.getId());
        } catch (Exception e) {
            System.out.println("Error Create Ads: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed_create_ads.message ");
        }
    }

    @GetMapping("/Status-Unit/Ads")
    public ResponseEntity<?> getAdsByUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());


        List<Ads> ads = adsService.getAdsForUserAndStatus(userId, statusUnitId, pageable);

        if (!ads.isEmpty()) {
            List<AdsResponseStatusDto> adsDtoList = adsStatusMapper.toAdsDtoList(ads);
            return new ResponseEntity<>(adsDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(200, "No_content.message");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @DeleteMapping("Delete/Ads/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Long id) {

        Ads ads = adsRepository.findById(id).orElse(null);

        StatusUnit statusUnit = statusUnitRepository.findById(4L).orElse(null);

        ads.setStatusUnit(statusUnit);

        adsRepository.save(ads);

        ApiResponse response = new ApiResponse(200, "Ads_deleted.message");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping("/Accepted/Status")
    public ResponseEntity<?> getAdsByAcceptedStatus() {
        try {
            List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
            System.out.println("Exception Accepted Ads: " + e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No_content.message"));
        }
    }

    @GetMapping("/{userId}/check-package-ads")
    public ResponseEntity<?> checkUserPackageAds(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPackageAds() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseFlag(200,"false", false));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseFlag(204,"true", true));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/packageAds/{packageAdsId}")
    public ResponseEntity<?> setPackageAdsForUser(@PathVariable Long userId, @PathVariable Long packageAdsId) {
        try {
            User user = userService.setPackageAdsForUser(userId, packageAdsId);

//            return ResponseEntity.ok("PackageAds set successfully for user. User's wallet balance is now: " + user.getWallet());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Successful_package_ads.message" + user.getWallet()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"Fail_package_ads.message"));
//            return ResponseEntity.badRequest().body(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body()
        } catch (InsufficientFundsException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"Fail_package_ads.message"));
            return ResponseEntity.badRequest().body("Fail_package_ads_wallet.message");
        }
    }


}
