package com.AlTaraf.Booking.Service.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Evaluation.EvaluationRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Specifications.UnitSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    ReservationRepository reservationsRepository;

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
    public Page<UnitDtoFavorite> getUnitsAddedLastMonth(int page, int size) {
        LocalDateTime startOfLastMonth = LocalDateTime.now().minusDays(30);
        LocalDateTime endOfLastMonth = LocalDateTime.now();

        Date startDate = Date.from(startOfLastMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfLastMonth.atZone(ZoneId.systemDefault()).toInstant());

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> unitsPage = unitRepository.findByCreatedDateBetween(startDate, endDate, pageRequest);

        if (unitsPage.isEmpty()) {
            return Page.empty();
        }

        return unitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }
//    public Page<Unit> getFavoriteUnits(int page, int size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return unitRepository.findByFavoriteTrue(pageRequest);
//    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    @Override
    public Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> unitsPage = unitRepository.findByAccommodationType_Name(accommodationTypeName, pageRequest);

        return unitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    @Override
    public void updateImageDataUnit(Long unitId) {
        // Fetch the Unit by ID
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            // Retrieve associated ImageData entities without a unit
            List<ImageData> imageDataList = imageDataRepository.findByUnitIsNull();

            // Update the unit for each ImageData entity
            for (ImageData imageData : imageDataList) {
                imageData.setUnit(unit);
                imageDataRepository.save(imageData);
            }
        } else {
            // Handle the case when the Unit with the specified ID is not found
            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
        }

    }

//    @Override
//    public void updateImageDataAds(Long adsId) {
//        // Fetch the Unit by ID
//        Ads ads = adsRepository.findById(adsId).orElse(null);
//
//        if (ads != null) {
//            // Retrieve associated ImageData entities without a unit
//            List<ImageData> imageDataList = imageDataRepository.findByUnitIsNull();
//
//            // Update the unit for each ImageData entity
//            for (ImageData imageData : imageDataList) {
//                imageData.setUnit(unit);
//                imageDataRepository.save(imageData);
//            }
//        } else {
//            // Handle the case when the Unit with the specified ID is not found
//            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
//        }
//
//    }

    @Override
    public Page<UnitDtoFavorite> getAllUnitDtoFavorites(Pageable pageable) {
        Page<Unit> unitPage = unitRepository.findAll(pageable);
        return unitPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<Unit> getAllUnit(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }

    @Override
    public List<Unit> getAllUnitForMap() {
        return unitRepository.findAll();
    }

//    @Override
//    public Page<Unit> getAllUnits(Pageable pageable) {
//        return unitRepository.findAll(pageable);
//    }

    @Override
    public Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable) {
        return unitRepository.findByNameUnitContainingIgnoreCase(nameUnit, pageable);
    }

    @Override
    public List<Unit> filterUnitsByNameForMap(String nameUnit) {
        return unitRepository.findByNameUnitContainingIgnoreCaseForMap(nameUnit);
    }


    @Override
    public List<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName) {
        // Retrieve a List of Units for the given USER_ID and StatusUnit name
        return unitRepository.findAllByUserIdAndStatusUnitName(userId, statusUnitName);
    }

    @Override
    public Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable) {
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
    public List<Unit> getUnitTypeIdForMap(Long unitTypeId) {
        return unitRepository.findByUnitType_IdForMap(unitTypeId);
    }


    public List<Unit> getUnitsByUserId(Long userId) {
        return unitRepository.findByUserId(userId);
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

//        if (newPriceHall != 0) {
//            spec = spec.and(UnitSpecifications.byNewPriceHall(newPriceHall));
//        }

        return unitRepository.findAll(spec);
    }


    @Override
    public List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
                                         Long unitTypeId, Long accommodationTypeId, Set<Long> hotelClassificationIds,
                                         Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                         Set<Long> evaluationIds, int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax) {
        Specification<Unit> spec = Specification.where(null);

        if (cityId != null) {
            spec = spec.and(UnitSpecifications.byCity(cityId));
        }

        if (regionId != null) {
            spec = spec.and(UnitSpecifications.byRegion(regionId));
        }

        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
        }

        if (availablePeriodsId != null) {
            spec = spec.and(UnitSpecifications.byAvailablePeriod(availablePeriodsId));
        }

//        if (newPriceHall != 0) {
//            spec = spec.and(UnitSpecifications.byNewPriceHall(newPriceHall));
//        }

        if (unitTypeId != null) {
            spec = spec.and(UnitSpecifications.byUnitTypeId(unitTypeId));
        }

        if (accommodationTypeId != null) {
            spec = spec.and(UnitSpecifications.byAccommodationTypeId(accommodationTypeId));
        }

        if (hotelClassificationIds != null) {
//            spec = spec.and(UnitSpecifications.byHotelClassificationId(hotelClassificationId));
            spec = spec.and(UnitSpecifications.byHotelClassificationIds(hotelClassificationIds));
        }

        if (evaluationIds != null) {
//            spec = spec.and(UnitSpecifications.byHotelClassificationId(hotelClassificationId));
            spec = spec.and(UnitSpecifications.byEvaluationIds(evaluationIds));
        }

        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
        }

        if (subFeaturesIds != null && !subFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.bySubFeaturesIds(subFeaturesIds));
        }

        if (foodOptionsIds != null && !foodOptionsIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.byFoodOptionsIds(foodOptionsIds));
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
    public void updateStatusForUser(Long unitId, Long statusUnitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + unitId));

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        unit.setStatusUnit(statusUnit);
        unitRepository.save(unit);
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
            // Update the existing Evaluation entity associated with the Unit
//            Evaluation evaluation = unit.getEvaluation();
//            if (evaluation == null) {
//                evaluation = new Evaluation();
//                unit.setEvaluation(evaluation);
//            }
//            evaluation.setId(evaluationId);

            unit.incrementTotalEvaluation();
            // Save the unit
            unitRepository.save(unit);
        }
    }

    public void updateEvaluationsForUnits(Long unitId) {
            calculateAndSetAverageEvaluation(unitId);

    }
}
