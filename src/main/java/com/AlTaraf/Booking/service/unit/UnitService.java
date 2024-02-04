package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {

    Unit saveUnit(Unit unit);

    Page<Unit> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size);

    Page<Unit> getFavoriteUnits(int page, int size);

    Unit getUnitById(Long id);

    Page<Unit> getUnitsAddedLastMonth(int page, int size);

    Page<Unit> getAllPendingUnits(int page, int size);

    Page<Unit> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size);

    void deleteUnit(Long id);

    void updateImageDataUnit(Long unitId);

    Page<Unit> getAllUnits(Pageable pageable);

    Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable);

    List<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName);
}
