package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.entity.unit.Unit;
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

//    Page<Unit> getAllUnits(Pageable pageable);

    Page<UnitDtoFavorite> getAllUnitDtoFavorites(Pageable pageable);

    Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable);

    List<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName);

    Page<UnitDtoFavorite> getUnitsByUserCity(Long userId, Pageable pageable);

    List<Unit> getUnitsByUnitTypeId(Long unitTypeId);

//    void updateRoomDetailsForUnit(Long unitId);

    List<Unit> getUnitsByUserId(Long userId);

    List<Unit> findUnitsByCriteria(Long cityId, Long regionId, Long availablePeriodId, int newPriceHall);

    List<Unit> findUnitsByFilters(Long cityId, Long regionId, Long availablePeriodsId, int newPriceHall,
                                  Long unitTypeId, Long accommodationTypeId, Long hotelClassificationId,
                                  Set<Long> basicFeaturesIds, Set<Long> subFeaturesIds, Set<Long> foodOptionsIds,
                                  int adultsAllowed, int childrenAllowed);

}
