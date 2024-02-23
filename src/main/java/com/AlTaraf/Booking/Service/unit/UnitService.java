package com.AlTaraf.Booking.Service.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UnitService {

    Unit saveUnit(Unit unit);

    Page<UnitDtoFavorite> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size);

    Page<UnitDtoFavorite> getFavoriteUnitsForUser(Long userId, int page, int size);
    Unit getUnitById(Long id);

    Page<UnitDtoFavorite> getUnitsAddedLastMonth(int page, int size);

    Page<UnitDtoFavorite> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size);

    void deleteUnit(Long id);

    void updateImageDataUnit(Long unitId);

//    void updateImageDataAds(Long adsId);

//    Page<Unit> getAllUnits(Pageable pageable);

    Page<UnitDtoFavorite> getAllUnitDtoFavorites(Pageable pageable);
    List<Unit> getAllUnitForMap();
    Page<Unit> getAllUnit(Pageable pageable);

    List<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName);

    Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable);

    Page<Unit> getUnitsByUnitTypeId(Long unitTypeId, Pageable pageable);
    List<Unit> getUnitTypeIdForMap(Long unitTypeId);

//    void updateRoomDetailsForUnit(Long unitId);

    List<Unit> getUnitsByUserId(Long userId);

    List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId,
                                  Long unitTypeId, Long accommodationTypeId, Set<Long> hotelClassificationIds,
                                  Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                  Set<Long> evaluationId,
                                  int capacityHalls, int adultsAllowed, int childrenAllowed, int priceMin, int priceMax);

    Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable);


    Page<Unit> filterUnitsByNameAndTypeId(String nameUnit, Long unitTypeId, Pageable pageable);

    List<Unit> filterUnitsByNameForMap(String nameUnit);

    List<Unit> filterUnitsByNameAndTypeIdForMap(String nameUnit, Long unitTypeId);

    Page<Unit> filterUnitsByRoomAvailableName(String roomAvailableName, Pageable pageable);
    Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable);

    Page<Unit> filterUnitsByAvailableAreaName(String availableAreaName, Pageable pageable);

    Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable);

    void updateStatusForUser(Long userId, Long statusUnitId);

    void calculateAndSetAverageEvaluation(Long unitId);

    void updateEvaluationsForUnits(Long unitId);
}
