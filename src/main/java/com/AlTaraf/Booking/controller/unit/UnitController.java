package com.AlTaraf.Booking.controller.unit;

import com.AlTaraf.Booking.dto.Unit.UnitDto;
import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.mapper.Unit.UnitMapper;
import com.AlTaraf.Booking.mapper.Unit.UnitRequestMapper;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.AvailablePeriods.AvailablePeriodsService;
import com.AlTaraf.Booking.service.unit.FeatureForHalls.FeatureForHallsService;
import com.AlTaraf.Booking.service.unit.RoomAvailable.RoomAvailableService;
import com.AlTaraf.Booking.service.unit.UnitService;
import com.AlTaraf.Booking.service.unit.feature.FeatureService;
import com.AlTaraf.Booking.service.unit.statusUnit.StatusUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    StatusUnitService statusUnitService;

    @Autowired
    RoomAvailableService roomAvailableService;

    @Autowired
    FeatureService featureService;









    @Autowired
    FeatureForHallsService featureForHallsService;

    @Autowired
    AvailablePeriodsService availablePeriodsService;

    @Autowired
    UnitMapper unitMapper;

    @Autowired
    UnitRequestMapper unitRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);


    @PostMapping("/create-unit")
    public ResponseEntity<?> createUnit(@RequestBody UnitRequestDto unitRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Unit unitToSave = unitRequestMapper.toUnit(unitRequestDto);

            // Save the unit in the database
            unitService.saveUnit(unitToSave);

            // Return success response
            return new ResponseEntity<>("Insert unit is successful", HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error occurred while processing create-unit request", e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to create unit. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response + " " + e);
        }
    }

    @GetMapping("/get-Units-By-Hotel-Classification-Names")
    public ResponseEntity<?> getUnitsByHotelClassificationNames(
            @RequestParam List<String> hotelClassificationNames,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Page<UnitDtoFavorite> units = unitService.getUnitsByHotelClassificationNames(hotelClassificationNames, page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units have high classification!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/status-unit")
    public ResponseEntity<?> getUnitsForUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitName") String statusUnitName) {

        List<Unit> units = unitService.getUnitsForUserAndStatus(userId, statusUnitName);

        if (!units.isEmpty()) {
            List<UnitDto> unitDtos = unitMapper.toUnitDtoList(units);
            return new ResponseEntity<>(unitDtos, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @GetMapping("/last-month")
    public ResponseEntity<?> getUnitsAddedLastMonth(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Page<UnitDtoFavorite> units = unitService.getUnitsAddedLastMonth(page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-Units-By-Accommodation-Type")
    public ResponseEntity<?> getUnitsByAccommodationType(
            @RequestParam String accommodationTypeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<UnitDtoFavorite> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeName, page, size);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units By Accommodation Type!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/units-by-user-city")
    public ResponseEntity<?> getUnitsByUserCity(@RequestParam Long userId) {
        List<UnitDtoFavorite> units = unitService.getUnitsByUserCity(userId);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units By User City!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @GetMapping("/{id}")
    public Unit getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {

        try {
            unitService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Unit deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
        ApiResponse response = new ApiResponse(404, "Not Found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public Page<Unit> getAllUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return unitService.getAllUnits(PageRequest.of(page, size));
    }
    // ========= END GET ALL UNITS =============

    // -----------------------------------------------------------------------------

    // ========= START FILTER UNIT BY NAME =============
    @GetMapping("/filter-unit-by-name")
    public Page<Unit> filterUnitsByName(
            @RequestParam String nameUnit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size));
    }
    // ========= END FILTER UNIT BY NAME =============

    // -----------------------------------------------------------------------------

    // ======== START GET ALL FEATURE For Halls ====================
    @GetMapping("/get-All-Feature-For-Halls")
    public ResponseEntity<List<FeatureForHalls>> getAllFeatureForHalls() {
        List<FeatureForHalls> featureForHalls = featureForHallsService.getAllFeatureForHalls();

        if (!featureForHalls.isEmpty()) {
            return new ResponseEntity<>(featureForHalls, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== End GET ALL FEATURE For All ====================

    // -----------------------------------------------------------------------------

    // ======== START GET ALL AvailablePeriods ====================
    @GetMapping("/get-AvailablePeriods")
    public ResponseEntity<List<AvailablePeriods>> getAllAvailablePeriods() {
        List<AvailablePeriods> availablePeriods = availablePeriodsService.getAllAvailablePeriods();

        if (!availablePeriods.isEmpty()) {
            return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // ======== End GET ALL AvailablePeriods ====================

    // -----------------------------------------------------------------------------

}
