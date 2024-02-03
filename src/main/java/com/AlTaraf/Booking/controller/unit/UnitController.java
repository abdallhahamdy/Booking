package com.AlTaraf.Booking.controller.unit;

import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.service.foodOption.FoodOptionService;
import com.AlTaraf.Booking.service.unit.AccommodationType.AccommodationTypeService;
import com.AlTaraf.Booking.service.unit.RoomAvailable.RoomAvailableService;
import com.AlTaraf.Booking.service.unit.UnitService;
import com.AlTaraf.Booking.service.unit.UnitType.UnitTypeService;
import com.AlTaraf.Booking.service.unit.feature.FeatureService;
import com.AlTaraf.Booking.service.unit.hotelClassification.HotelClassificationService;
import com.AlTaraf.Booking.service.unit.statusUnit.StatusUnitService;
import com.AlTaraf.Booking.service.unit.subFeature.SubFeatureService;
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
    @Autowired
    StatusUnitService statusUnitService;

    @Autowired
    RoomAvailableService roomAvailableService;

    @Autowired
    FeatureService featureService;

    @Autowired
    SubFeatureService subFeatureService;

    @Autowired
    FoodOptionService foodOptionService;

    @Autowired
    UnitTypeService unitTypeService;

    @Autowired
    AccommodationTypeService accommodationTypeService;

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

    @PatchMapping("/{id}/set-favorite")
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

    @PatchMapping("/{id}/delete-favorite")
    public ResponseEntity<Unit> deleteFavorite(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);

        if (unit != null) {
            unit.setFavorite(false); // Set favorite to true
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

    // =========== START ROOM AVAILABLE ===========
    @GetMapping("/get-All-Room-Available")
    public ResponseEntity<List<RoomAvailable>> getAllRoomAvailable() {
        List<RoomAvailable> roomAvailables = roomAvailableService.getAllRoomAvailable();

        if (!roomAvailables.isEmpty()) {
            return new ResponseEntity<>(roomAvailables, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // =========== END ROOM AVAILABLE ===========

    // -----------------------------------------------------------------------------

    // =========== START STATUS UNIT ==================
    @GetMapping("/get-All-Status-Unit")
    public ResponseEntity<List<StatusUnit>> getAllStatusUnit() {
        List<StatusUnit> statusUnitList = statusUnitService.getAllStatusUnit();

        if (!statusUnitList.isEmpty()) {
            return new ResponseEntity<>(statusUnitList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // =========== END STATUS UNIT ==================

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

    // ======== START GET UNITS DEPEND ON STATUS UNIT =======
    @GetMapping("/pending")
    public ResponseEntity<Page<Unit>> getAllPendingUnits(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int size) {
        Page<Unit> units = unitService.getAllPendingUnits(page, size);
        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET UNITS DEPEND ON STATUS UNIT =======

    // -----------------------------------------------------------------------------

    // ======== START GET ALL FEATURE ====================
    @GetMapping("/get-All-Feature")
    public ResponseEntity<List<Feature>> getAllFeature() {
        List<Feature> featureList = featureService.getAllFeature();

        if (!featureList.isEmpty()) {
            return new ResponseEntity<>(featureList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET ALL FEATURE ======================

    // -----------------------------------------------------------------------------

    // ======== START GET SUB FEATURE ====================
    @GetMapping("/get-Sub-Feature")
    public ResponseEntity<List<SubFeature>> getAllSubFeature() {
        List<SubFeature> subFeatureList = subFeatureService.getAllSubFeature();

        if (!subFeatureList.isEmpty()) {
            return new ResponseEntity<>(subFeatureList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET SUB FEATURE ======================

    // -----------------------------------------------------------------------------

    // ======== START GET SUB FEATURE ====================
    @GetMapping("/get-Food-Option")
    public ResponseEntity<List<FoodOption>> getAllFoodOption() {
        List<FoodOption> foodOptionList = foodOptionService.getAllFoodOption();

        if (!foodOptionList.isEmpty()) {
            return new ResponseEntity<>(foodOptionList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET SUB FEATURE ======================

    // -----------------------------------------------------------------------------

    // ======== START GET ALL UNIT TYPE ====================
    @GetMapping("/get-Unit-Type")
    public ResponseEntity<List<UnitType>> getAllUnitType() {
        List<UnitType> unitTypeList = unitTypeService.getAllUnitType();

        if (!unitTypeList.isEmpty()) {
            return new ResponseEntity<>(unitTypeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET ALL UNIT TYPE ======================

    // -----------------------------------------------------------------------------

    // ======== START GET ALL ACCOMMODATION TYPE ====================
    @GetMapping("/get-Accommodation-Type")
    public ResponseEntity<List<AccommodationType>> getAccommodationType() {
        List<AccommodationType> accommodationTypeList = accommodationTypeService.getAllAccommodationType();

        if (!accommodationTypeList.isEmpty()) {
            return new ResponseEntity<>(accommodationTypeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET ALL ACCOMMODATION TYPE ======================

    // -----------------------------------------------------------------------------

    // ======== START GET UNIT BY ACCOMMODATION TYPE ====================
    @GetMapping("/get-Units-By-Accommodation-Type")
    public ResponseEntity<Page<Unit>> getUnitsByAccommodationType(
            @RequestParam String accommodationTypeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<Unit> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeName, page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== END GET UNIT BY ACCOMMODATION TYPE ====================

    // -----------------------------------------------------------------------------

    // ======== START GET UNIT BY ID ====================
    @GetMapping("/{id}")
    public Unit getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id);
    }
    // ======== GET GET UNIT BY ID ====================

    // -----------------------------------------------------------------------------

    // ========= START DELETE UNIT =============
    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
    }
    // ========= END DELETE UNIT =============

}
