package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.repository.Ads.AdsRepository;
import com.AlTaraf.Booking.repository.image.ImageDataRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import com.AlTaraf.Booking.repository.user.UserRepository;
import com.AlTaraf.Booking.specifications.UnitSpecifications;
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
    public Page<UnitDtoFavorite> getFavoriteUnitsForUser(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Unit> favoriteUnitsPage = unitRepository.findByUser_IdAndFavorite(userId, true, pageRequest);

        return favoriteUnitsPage.map(unitFavoriteMapper::toUnitFavoriteDto);
    }

    @Override
    public Page<UnitDtoFavorite> getUnitsAddedLastMonth(int page, int size) {
        LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfMonth = LocalDateTime.now();

        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Unit> unitsPage = unitRepository.findByCreatedDateBetween(startDate, endDate, pageRequest);

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


    public List<Unit> findUnitsByCriteria(Long cityId, Long regionId, Long availablePeriodId, int newPriceHall) {
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

        if (newPriceHall != 0) {
            spec = spec.and(UnitSpecifications.byNewPriceHall(newPriceHall));
        }

        return unitRepository.findAll(spec);
    }


    @Override
    public List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId, int newPriceHall,
                                         Long unitTypeId, Long accommodationTypeId, Set<Long> hotelClassificationIds,
                                         Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                         int capacityHalls, int adultsAllowed, int childrenAllowed) {
        Specification<Unit> spec = Specification.where(null);

        if (cityId != null) {
            spec = spec.and(UnitSpecifications.byCityId(cityId));
        }

        if (regionId != null) {
            spec = spec.and(UnitSpecifications.byRegionId(regionId));
        }

        if (basicFeaturesIds != null && !basicFeaturesIds.isEmpty()) {
            spec = spec.and(UnitSpecifications.byBasicFeaturesIds(basicFeaturesIds));
        }

        if (availablePeriodsId != null) {
            spec = spec.and(UnitSpecifications.byAvailablePeriodsId(availablePeriodsId));
        }

        if (newPriceHall != 0) {
            spec = spec.and(UnitSpecifications.byNewPriceHall2(newPriceHall));
        }

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
}
