package com.AlTaraf.Booking.controller.unit;

import com.AlTaraf.Booking.entity.unit.HotelClassification;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.service.unit.UnitService;
import com.AlTaraf.Booking.service.unit.hotelClassification.HotelClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    UnitService unitService;
    @Autowired
    HotelClassificationService hotelClassificationService;

    // -----------------------------------------------------------------------------

    // ============ START CREATE UNIT =============
    @PostMapping("/create-unit")
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        Unit savedUnit = unitService.saveUnit(unit);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }
    // ============ END CREATE UNIT =============

    // -----------------------------------------------------------------------------

    // ========== START FAVORITE ==============

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<Unit> setFavorite(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);

        if (unit != null) {
            unit.setFavorite(true); // Set favorite to true
            unitService.saveUnit(unit);
            return new ResponseEntity<>(unit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<Page<Unit>> getFavoriteUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Page<Unit> favoriteUnits = unitService.getFavoriteUnits(page, size);

        if (!favoriteUnits.isEmpty()) {
            return new ResponseEntity<>(favoriteUnits, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ========== END FAVORITE ===============

    // -----------------------------------------------------------------------------

    // =========== START HOTEL CLASSIFICATION ===========
    @GetMapping("/get-All-Hotel-Classifications")
    public ResponseEntity<List<HotelClassification>> getAllHotelClassifications() {
        List<HotelClassification> classifications = hotelClassificationService.getAllHotelClassification();

        if (!classifications.isEmpty()) {
            return new ResponseEntity<>(classifications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-Units-By-Hotel-Classification-Names")
    public ResponseEntity<Page<Unit>> getUnitsByHotelClassificationNames(
            @RequestParam List<String> hotelClassificationNames,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Page<Unit> units = unitService.getUnitsByHotelClassificationNames(hotelClassificationNames, page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // =========== END HOTEL CLASSIFICATION ===========

    // -----------------------------------------------------------------------------

    // ======== START CREATED DATE BETWEEN ==============
    @GetMapping("/added-today")
    public ResponseEntity<Page<Unit>> getUnitsAddedToday(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Page<Unit> units = unitService.getUnitsAddedToday(page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END CREATED DATE BETWEEN ==============

    // -----------------------------------------------------------------------------
}
