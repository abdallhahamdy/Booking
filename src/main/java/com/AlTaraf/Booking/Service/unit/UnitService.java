package com.AlTaraf.Booking.Service.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface UnitService {

    Unit saveUnit(Unit unit);

    Page<UnitDtoFavorite> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size);

    Page<UnitDtoFavorite> getUnitByEvaluationInOrderByEvaluationScoreDesc(int page, int size);
    Page<UnitDtoFavorite> getFavoriteUnitsForUser(Long userId, int page, int size);
    Unit getUnitById(Long id);

    Page<UnitDtoFavorite> getUnitsAddedLastMonth(int page, int size, Sort sort);

    Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size, Sort sort);
    Page<UnitDashboard> getUnitsByAccommodationTypeNameDashboard(String accommodationTypeName, int page, int size);

    void deleteUnit(Long id);

    void updateImageDataUnit( Long unitId, Long userId);

    void updateImageDataAds( Long adsId, Long userId);

    Page<UnitDtoFavorite> getAllUnitDtoFavorites(Pageable pageable);
    List<Unit> getAllUnitForMap();
    Page<Unit> getAllUnit(Pageable pageable);

    Page<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName, Pageable pageable);
    Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable, Sort sort);

    Page<Unit> getUnitsByUnitTypeId(Long unitTypeId, Pageable pageable);
    List<Unit> getUnitTypeIdForMap(Long unitTypeId);

    Page<Unit> getUnitsByUserId(Long userId, Pageable pageable);

    List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
                                  Long unitTypeId, Long accommodationTypeId, Set<Long> hotelClassificationIds,
                                  Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                  Set<Long> evaluationId,
                                  int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax);

    Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable);


    Page<Unit> filterUnitsByNameAndTypeId(String nameUnit, Long unitTypeId, Pageable pageable);

    Page<Unit> filterUnitsByUserNameAndTypeId(String userName, Long unitTypeId, Pageable pageable);

    Page<Unit> filterUnitsByPhoneNumberAndTypeId(String phoneNumber, Long unitTypeId, Pageable pageable);

    List<Unit> filterUnitsByNameForMap(String nameUnit);

    List<Unit> filterUnitsByNameAndTypeIdForMap(String nameUnit, Long unitTypeId);

    Page<Unit> filterUnitsByRoomAvailableName(String roomAvailableName, Pageable pageable);
    Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable);

    Page<Unit> filterUnitsByAvailableAreaName(String availableAreaName, Pageable pageable);

    Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable);

    void updateStatusForUser(Long userId, Long statusUnitId);

    void calculateAndSetAverageEvaluation(Long unitId);

    void updateEvaluationsForUnits(Long unitId);

    @Transactional
    void deleteUnitWithDependencies(Long id);

    Unit getUnitById(Long unitId, Sort sort);

}
