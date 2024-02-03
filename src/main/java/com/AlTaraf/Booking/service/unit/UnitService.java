package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnitService {

    // ========= SAVE UNIT ===========
    Unit saveUnit(Unit unit);

    // ========= GET UNITS BY HOTEL CLASSIFICATION NAMES ===============
    Page<Unit> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size);

    // ========= GET FAVORITE UNITS ==========
    Page<Unit> getFavoriteUnits(int page, int size);

    // ========= GET UNIT BY ID =============
    Unit getUnitById(Long id);

    // ======== GET ALL UNITS ADDED TODAY =======
    Page<Unit> getUnitsAddedToday(int page, int size);

    // ======== GET UNITS WHICH HIS STATUS IS PENDING =======
    Page<Unit> getAllPendingUnits(int page, int size);

    // ======== GET UNITS BY ACCOMMODATION TYPE NAME =======
    Page<Unit> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size);

    // ========= DELETE UNIT =============
    void deleteUnit(Long id);

    // =========  SET UNIT ID IN IMAGE  =============
    void updateImageDataUnit(Long unitId);
}
