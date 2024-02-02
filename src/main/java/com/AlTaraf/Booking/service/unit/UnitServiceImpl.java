package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    // --------------------------------------------------

    // ========= START SAVE UNIT ===========
    public Unit saveUnit(Unit unit) {
        try {
            return unitRepository.save(unit);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e; // Rethrow the exception or handle it accordingly
        }
    }
    // ========= END SAVE UNIT ===========

    // --------------------------------------------------

    // ========= START GET UNITS BY HOTEL CLASSIFICATION NAMES ===============
    public Page<Unit> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByHotelClassification_HotelClassificationNameIn(hotelClassificationNames, pageRequest);
    }
    // ========= END GET UNITS BY HOTEL CLASSIFICATION NAMES ===============

    // --------------------------------------------------

    // ========= START GET FAVORITE UNITS ==========
    public Page<Unit> getFavoriteUnits(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByFavoriteTrue(pageRequest);
    }
    // ========= START GET FAVORITE UNITS ==========

    // --------------------------------------------------

    // ========= START GET UNIT BY ID =============
    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }
    // ========= END GET UNIT BY ID ================

    // --------------------------------------------------
}
