package com.AlTaraf.Booking.Controller.Ads;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
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
import com.AlTaraf.Booking.Payload.request.Ads.AdsResponseStatusDto;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
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
    private MessageSource messageSource;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    StatusRepository statusUnitRepository;

    @Autowired
    PackageAdsRepository packageAdsRepository;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/package-ads")
    public ResponseEntity<?> getAllPackageAds() {
        try {
            List<PackageAds> allPackageAds = adsService.getAllPackageAds();
            return new ResponseEntity<>(allPackageAds, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception here
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }


    @GetMapping("units/by-user-id/{userId}")
    public ResponseEntity<?> getUnitsByUserId(@PathVariable Long userId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, PageRequest.of(page, size));

        if (unitsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_unit_for_user.message: " + userId, null, LocaleContextHolder.getLocale())+ userId));
        } else {
            List<UnitDtoFavorite> unitGeneralResponseDtos = unitsPage.getContent().stream()
                    .map(unitFavoriteMapper::toUnitFavoriteDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(unitGeneralResponseDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAds(@RequestBody AdsRequestDto adsRequestDto) throws IOException, InterruptedException {
            User user = userRepository.findByUserId(adsRequestDto.getUserId());
            PackageAds packageAds = packageAdsRepository.findById(0L).orElse(null);

            Ads existAds = null;
            existAds = adsService.getByUserIdAndUnitId(adsRequestDto.getUserId(), adsRequestDto.getUnitId());

            if (existAds != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("exist_ads.message", null, LocaleContextHolder.getLocale()));
            }

            int numberAds = user.getNumberAds();
            if (numberAds > 0) {
                numberAds--;
                user.setNumberAds(numberAds);
            }

            if (numberAds == 0) {
                user.setPackageAds(packageAds);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("package_ads_null.message", null, LocaleContextHolder.getLocale()));
            }

            userRepository.save(user);

            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_ads.message", null, LocaleContextHolder.getLocale()),user.getId());
            notificationService.processNotification(notificationRequest);
            Ads ads = adsService.createAds(adsMapper.toEntity(adsRequestDto));

            return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("ads_created.message", null, LocaleContextHolder.getLocale()));

//            System.out.println("Error Create Ads: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("failed_create_ads.message", null, LocaleContextHolder.getLocale()));

    }

    @GetMapping("/status-unit/ads")
    public ResponseEntity<?> getAdsByUserAndStatus(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());


        List<Ads> ads = adsService.getAdsForUserAndStatus(userId, statusUnitId, pageable);

        if (!ads.isEmpty()) {
            List<AdsResponseStatusDto> adsDtoList = adsStatusMapper.toAdsDtoList(ads);
            return new ResponseEntity<>(adsDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(200, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @DeleteMapping("delete/ads/{id}")
    public ResponseEntity<?> deleteAds(@PathVariable Long id) {

        Ads ads = adsRepository.findById(id).orElse(null);

        StatusUnit statusUnit = statusUnitRepository.findById(4L).orElse(null);

        ads.setStatusUnit(statusUnit);

        adsRepository.save(ads);


        ApiResponse response = new ApiResponse(200, messageSource.getMessage("ads_deleted.message", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @GetMapping("/accepted/status")
    public ResponseEntity<?> getAdsByAcceptedStatus() {
        try {
            List<adsForSliderResponseDto> ads = adsService.getAdsByAcceptedStatus();
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
            System.out.println("Exception Accepted Ads: " + e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }

//    @PostMapping("/{userId}/package-ads/{packageAdsId}")
//    public ResponseEntity<?> setPackageAdsForUser(@PathVariable Long userId, @PathVariable Long packageAdsId) {
//        try {
//            User user = userService.setPackageAdsForUser(userId, packageAdsId);
//
//            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,messageSource.getMessage("successful_package_ads.message", null, LocaleContextHolder.getLocale()) + " " + user.getWallet()));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,messageSource.getMessage("fail_package_ads.message", null, LocaleContextHolder.getLocale())));
//        } catch (InsufficientFundsException e) {
//            return ResponseEntity.badRequest().body(messageSource.getMessage("fail_package_ads_wallet.message", null, LocaleContextHolder.getLocale()));
//        }
//    }

    @PostMapping("/{userId}/package-ads/{packageAdsId}")
    public ResponseEntity<?> setPackageAdsForUser(@PathVariable Long userId, @PathVariable Long packageAdsId) throws InsufficientFundsException {


            User user = userRepository.findByUserId(userId);

            PackageAds packageAds = packageAdsRepository.findById(packageAdsId).orElse(null);

            if (user.getPackageAds() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(402,messageSource.getMessage("fail_package_ads_exist.message", null, LocaleContextHolder.getLocale()) + ": " + user.getNumberAds()));
            }

            if(user.getWallet() < packageAds.getPrice()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("fail_package_ads_wallet.message", null, LocaleContextHolder.getLocale()));
            }

             user = userService.setPackageAdsForUser(userId, packageAdsId);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,messageSource.getMessage("successful_package_ads.message", null, LocaleContextHolder.getLocale()) + " " + user.getWallet()));
        }

}
