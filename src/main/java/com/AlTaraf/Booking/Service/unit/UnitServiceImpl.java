package com.AlTaraf.Booking.Service.unit;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.Calender.ReserveDateRoomDetails;
import com.AlTaraf.Booking.Entity.Calender.ReserveDateUnit;
import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Unit.Dashboard.UnitDashboardMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Payload.response.CounterUnits;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Evaluation.EvaluationRepository;
import com.AlTaraf.Booking.Repository.File.FileForAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForUnitRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRoomDetailsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateUnitRepository;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportUnitRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Specifications.UnitSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    private FileForUnitRepository fileForUnitRepository;

    @Autowired
    private FileForAdsRepository fileForAdsRepository;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    private UnitDashboardMapper unitDashboardMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    ReservationRepository reservationsRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReserveDateRepository reserveDateRepository;

    @Autowired
    ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    ReserveDateRoomDetailsRepository reserveDateRoomDetailsRepository;

    @Autowired
    ReserveDateUnitRepository reserveDateUnitRepository;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    AdsService adsService;

    @Autowired
    UnitService unitService;

    @Autowired
    TechnicalSupportUnitRepository technicalSupportUnitRepository;

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageSource messageSource;

    public Unit saveUnit(Unit unit) {
        try {
            return unitRepository.save(unit);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Page<UnitDtoFavorite> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Unit> unitsPage = unitRepository.findByHotelClassification_NameIn(hotelClassificationNames, pageRequest);

        return unitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<UnitDtoFavorite> getUnitByEvaluationInOrderByEvaluationScoreDesc(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> units = unitRepository.findByEvaluationInOrderByEvaluationScoreDesc(pageRequest);

        return units.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<UnitDtoFavorite> getFavoriteUnitsForUser(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Unit> favoriteUnitsPage = unitRepository.findByUser_IdAndFavorite(userId, true, pageRequest);

        return favoriteUnitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public List<UnitDtoFavorite> getNewlyAdded() {



        List<Unit> units = unitRepository.findByNewlyAdded();


        return units.stream()
                .map(unitFavoriteMapper::toUnitFavoriteDto)
                .collect(Collectors.toList());
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    public Unit getUnitById(Long unitId, Sort sort) {
        List<Unit> units = unitRepository.findById(unitId)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());

        units.sort(Comparator.comparing(Unit::getId)); // Sort the list by unit ID

        return units.isEmpty() ? null : units.get(0);
    }
    @Override
    public Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(Long accommodationTypeId, int page, int size, Sort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Unit> unitsPage = unitRepository.findByAccommodationType_Id(accommodationTypeId, pageRequest);

        return unitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<UnitDashboard> getUnitsByAccommodationTypeNameDashboard(Long accommodationTypeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> unitsPage = unitRepository.findByAccommodationType_Id(accommodationTypeId, pageRequest);

        return unitsPage.map(unitDashboardMapper::toUnitDashboard);
    }

    @Override
    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    @Override
    public void updateImageDataUnit(Long unitId, Long userId) {
        // Fetch the Unit by ID
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            // Retrieve associated ImageData entities
            List<FileForUnit> fileForUnitList = fileForUnitRepository.findByUserIdAndUnitIsNull(userId);

            // Update the unit for each ImageData entity
            for (FileForUnit fileForUnit : fileForUnitList) {
                fileForUnit.setUnit(unit);
                fileForUnitRepository.save(fileForUnit);
            }
        }
        else {
            // Handle the case when the Unit with the specified ID is not found
            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
        }

    }

    @Override
    public void updateImageDataAds( Long adsId, Long userId) {

        // Fetch the Ads by ID
        Ads ads = adsRepository.findById(adsId).orElse(null);
//        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (ads != null) {
            // Retrieve associated FileForAds entities
            List<FileForAds> fileForAdsList = fileForAdsRepository.findByUserIdAndAdsIsNull(userId);

            // Update the unit for each ImageData entity
            for (FileForAds fileForAds : fileForAdsList) {
                System.out.println("Name Image: "+ fileForAds.getName());
                fileForAds.setAds(ads);//                imageData.setUnit(unit);

                fileForAdsRepository.save(fileForAds);
            }
        }
        else {
            // Handle the case when the Unit with the specified ID is not found
            throw new EntityNotFoundException("Unit not found with ID: " + adsId);
        }
    }


    @Override
    public Page<UnitDtoFavorite> getAllUnitDtoFavorites(Pageable pageable) {
        Page<Unit> unitPage = unitRepository.findAll(pageable);
        return unitPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<Unit> getAllUnit(Pageable pageable) {
        return unitRepository.findAllByStatusUnitId(pageable);
    }

    @Override
    public List<Unit> getAllUnitForMap() {
        return unitRepository.findAll();
    }

    @Override
    public Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable) {
        return unitRepository.findByNameUnitContainingIgnoreCase(nameUnit, pageable);
    }

    @Override
    public List<Unit> filterUnitsByNameForMap(String nameUnit) {
        return unitRepository.findByNameUnitContainingIgnoreCaseForMap(nameUnit);
    }


    @Override
    public Page<Unit> getUnitsForUserAndStatus(Long userId, Long statusUnitId, Pageable pageable) {
        return unitRepository.findAllByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    @Override
    public Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable, Sort sort) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || user.getCity() == null) {
            return Page.empty(); // Return an empty page if user or user's city is not found
        }

        City userCity = user.getCity();
        Page<Unit> unitPage = unitRepository.findByCity(userCity, pageable);

        return unitPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<Unit> getUnitsByUnitTypeId(Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUnitType_Id(unitTypeId, pageable);
    }

    @Override
    public Page<Unit> getUnitsByUnitTypeIdForDashboard(Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUnitType_IdForDashboard(unitTypeId, pageable);
    }

    @Override
    public List<Unit> getUnitTypeIdForMap(Long unitTypeId) {
        return unitRepository.findByUnitType_IdForMap(unitTypeId);
    }


    public Page<Unit> getUnitsByUserId(Long userId, Pageable pageable) {
        return unitRepository.findByUserId(userId, pageable);
    }

    public List<Unit> findUnitsByCriteria(Long cityId, Long regionId, Long availablePeriodId) {
        Specification<Unit> spec = Specification.where(null);

        if (cityId != null) {
            spec = spec.and(UnitSpecifications.byCity(cityId));
        }

        if (regionId != null) {
            spec = spec.and(UnitSpecifications.byRegion(regionId));
        }

        if (availablePeriodId != null) {
            spec = spec.and(UnitSpecifications.byAvailablePeriod(availablePeriodId));
        }

        return unitRepository.findAll(spec);
    }


    @Override
    public List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
                                         Long unitTypeId, Set<Long> accommodationTypeIds, Set<Long> hotelClassificationIds,
                                         Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                         Set<Long> evaluationIds, int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax) {
        Specification<Unit> spec = Specification.where(null);

        if (cityId != null) {
            spec = spec.and(UnitSpecifications.byCity(cityId));
        }

        if (regionId != null) {
            spec = spec.and(UnitSpecifications.byRegion(regionId));
        }

//        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
//            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
//        }

        if (availablePeriodsId != null) {
            spec = spec.and(UnitSpecifications.byAvailablePeriod(availablePeriodsId));
        }


        if (unitTypeId != null) {
            spec = spec.and(UnitSpecifications.byUnitTypeId(unitTypeId));
        }

        if (accommodationTypeIds != null) {
            spec = spec.and(UnitSpecifications.byAccommodationTypeIds(accommodationTypeIds));
        }

        if (hotelClassificationIds != null) {
            spec = spec.and(UnitSpecifications.byHotelClassificationIds(hotelClassificationIds));
        }

        if (evaluationIds != null) {
            spec = spec.and(UnitSpecifications.byEvaluationIds(evaluationIds));
        }

        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
        }

        if (subFeaturesIds != null && !subFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.bySubFeaturesIds(subFeaturesIds));
        }

        if (capacityHalls != 0) {
            spec = spec.and(UnitSpecifications.byCapacityHalls(capacityHalls));
        }

        if (adultsAllowed != 0) {
            spec = spec.and(UnitSpecifications.byAdultsAllowed(adultsAllowed));
        }
        if (childrenAllowed != 0) {
            spec = spec.and(UnitSpecifications.byChildrenAllowed(childrenAllowed));
        }

        if (priceMin > 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin));
        }

        if (priceMax > 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax));
        }

        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statusUnit").get("id"), 2));

        return unitRepository.findAll(spec);
    }

    @Override
    public List<Unit> filterUnitsByNameAndTypeIdForMap(String nameUnit, Long unitTypeId) {
        return unitRepository.findByNameUnitAndUnitTypeForMap(nameUnit, unitTypeId);
    }

    @Override
    public Page<Unit> filterUnitsByNameAndTypeId(String nameUnit, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByNameUnitAndUnitType(nameUnit, unitTypeId, pageable);
    }

    @Override
    public Page<Unit> filterUnitsByUserNameAndTypeId(String userName, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByUsernameAndUnitType(userName, unitTypeId, pageable);
    }

    @Override
    public Page<Unit> filterUnitsByPhoneNumberAndTypeId(String phoneNumber, Long unitTypeId, Pageable pageable) {
        return unitRepository.findByPhoneNumberAndUnitType(phoneNumber, unitTypeId, pageable);
    }

    @Override
    public Page<Unit> filterUnitsByRoomAvailableName(String roomAvailableName, Pageable pageable) {
        return unitRepository.findByRoomAvailableName(roomAvailableName, pageable);
    }

    @Override
    public Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable) {
        return unitRepository.findByNameUnitAndRoomAvailableNameContainingIgnoreCase(nameUnit, roomAvailableName, pageable);
    }

    @Override
    public Page<Unit> filterUnitsByAvailableAreaName(String availableAreaName, Pageable pageable) {
        return unitRepository.findByAvailableAreaName(availableAreaName, pageable);
    }

    @Override
    public Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable) {
        return unitRepository.findByNameUnitAndAvailableAreaNameContainingIgnoreCase(nameUnit, availableAreaName, pageable);
    }

    @Override
    public void updateStatusForUser(Long unitId, Long statusUnitId) throws IOException, InterruptedException {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + unitId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        unit.setStatusUnit(statusUnit);

        unitRepository.save(unit);

        if ( statusUnitId == 2) {
            System.out.println("statusUnitId");
//            PushNotificationRequest notificationRequest = new PushNotificationRequest("رسالة من الادمن",  unit.getNameUnit()+ "تم قبول طلب اضافة وحدة ",unit.getUser().getId());
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_accepted_units.message", null, LocaleContextHolder.getLocale()) + " " + unit.getNameUnit(), unit.getUser().getId());
            notificationService.processNotification(notificationRequest);
        } else if ( statusUnitId == 3) {
            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_rejected_units.message", null, LocaleContextHolder.getLocale()) + " " + unit.getNameUnit(), unit.getUser().getId());
            notificationService.processNotification(notificationRequest);
        }

    }

    @Override
    public void calculateAndSetAverageEvaluation(Long unitId) {
        List<Reservations> reservations = reservationsRepository.findByUnitId(unitId);
        double totalScore = 0.0;
        int count = 0;
        for (Reservations reservation : reservations) {
            if (reservation.getEvaluation() != null) {
                totalScore += reservation.getEvaluation().getScore(); // Assuming getScore() returns the evaluation score
                count++;
                System.out.println("I'm in for loop");
            }
        }
        if (count > 0) {
            double averageScore = totalScore / count;

            Long evaluationId;
            if (averageScore >= 9) {
                evaluationId = 1L; // Excellent: 9+
            } else if (averageScore >= 8) {
                evaluationId = 2L; // Very Good: 8+
            } else if (averageScore >= 7) {
                evaluationId = 3L; // Good: 7+
            } else if (averageScore >= 6) {
                evaluationId = 4L; // Acceptable: 6+
            } else {
                evaluationId = null; // Handle the case when averageScore is out of range
            }

            System.out.println("averageScore: " + averageScore);
            // Get the unit
            Unit unit = unitRepository.findById(unitId)
                    .orElseThrow(() -> new RuntimeException("Unit not found"));

            Evaluation evaluation = evaluationRepository.findById(evaluationId)
                    .orElseThrow(() -> new RuntimeException("evaluation not found"));

            System.out.println("Evaluation: " + evaluation.getId());


            unit.setEvaluation(evaluation);

            unit.incrementTotalEvaluation();

            // Save the unit
            unitRepository.save(unit);
        }
    }

    public void updateEvaluationsForUnits(Long unitId) {
            calculateAndSetAverageEvaluation(unitId);
    }

    @Transactional
    public void deleteUnitWithDependencies(Long id) {

        List<ReserveDate> reserveDateList = reserveDateRepository.findListByUnitId(id);
        for (ReserveDate reserveDate: reserveDateList) {
            reserveDateRepository.deleteDateInfoByReserveDateId(reserveDate.getId());
        }

        List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsRepository.findByUnitId(id);
        for (ReserveDateHalls reserveDateHalls: reserveDateHallsList) {
            reserveDateHallsRepository.deleteDateInfoHallsByReserveDateHallsId(reserveDateHalls.getId());
        }

        List<ReserveDateRoomDetails> reserveDateRoomDetailsList = reserveDateRoomDetailsRepository.findListByUnitId(id);
        for (ReserveDateRoomDetails reserveDateRoomDetails : reserveDateRoomDetailsList) {
            reserveDateRoomDetailsRepository.deleteDateInfoByReserveDateId(reserveDateRoomDetails.getId());
        }

        List<ReserveDateUnit> reserveDateUnitList = reserveDateUnitRepository.findListByUnitId(id);
        for (ReserveDateUnit reserveDateUnit : reserveDateUnitList) {
            reserveDateUnitRepository.deleteDateInfoByReserveDateId(reserveDateUnit.getId());
        }


        reserveDateHallsRepository.deleteByUnitId(id);
        reserveDateRepository.deleteByUnitId(id);
        reserveDateRoomDetailsRepository.deleteByUnitId(id);
        reserveDateUnitRepository.deleteByUnitId(id);

        userFavoriteUnitRepository.deleteByUnit(id);


        roomDetailsForAvailableAreaRepository.deleteByUnitId(id);
        roomDetailsRepository.deleteByUnitId(id);


        fileForUnitRepository.deleteByUnitId(id);
        reservationRepository.deleteByUnitId(id);

        technicalSupportUnitRepository.deleteByUnitId(id);

        Ads ads = adsRepository.findByUnitId(id);

        if (ads != null) {
            adsService.deleteAds(ads.getId());
        }

        unitService.deleteUnit(id);
    }


    @Override
    public void setCommissionForAllUnits(Double commission) {
        List<Unit> units = unitRepository.findAll();
        for (Unit unit : units) {
            unit.setCommission(commission);
        }
        unitRepository.saveAll(units);
    }

    @Override
    public CounterUnits getCounterForResidenciesUnits() {
        CounterUnits counterUnits = new CounterUnits();

        counterUnits.setCounterAllResidencies(unitRepository.countByAccommodationTypeIdNull());
        counterUnits.setCounterAllHotel(unitRepository.countByAccommodationTypeIdOne());
        counterUnits.setCounterAllHotelPartment(unitRepository.countByAccommodationTypeIdTwo());
        counterUnits.setCounterAllExternalPartment(unitRepository.countByAccommodationTypeIdThree());
        counterUnits.setCounterResort(unitRepository.countByAccommodationTypeIdFour());
        counterUnits.setCounterChalet(unitRepository.countByAccommodationTypeIdFive());
        counterUnits.setCounterlounge(unitRepository.countByAccommodationTypeIdSix());

        return counterUnits;
    }
}
