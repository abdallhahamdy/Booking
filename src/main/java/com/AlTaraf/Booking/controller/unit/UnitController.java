package com.AlTaraf.Booking.controller.unit;

import com.AlTaraf.Booking.dto.Unit.UnitDto;
import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.mapper.Unit.*;
import com.AlTaraf.Booking.mapper.Unit.RoomDetails.RoomDetailsRequestMapper;
import com.AlTaraf.Booking.payload.request.RoomDetails.RoomDetailsRequestDto;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.payload.response.Unit.EventHallsResponse;
import com.AlTaraf.Booking.payload.response.Unit.UnitGeneralResponseDto;
import com.AlTaraf.Booking.payload.response.Unit.UnitResidenciesResponseDto;
import com.AlTaraf.Booking.service.unit.AvailablePeriods.AvailablePeriodsService;
import com.AlTaraf.Booking.service.unit.FeatureForHalls.FeatureForHallsService;
import com.AlTaraf.Booking.service.unit.RoomAvailable.RoomAvailableService;
import com.AlTaraf.Booking.service.unit.RoomDetailsService.RoomDetailsService;
import com.AlTaraf.Booking.service.unit.UnitService;
import com.AlTaraf.Booking.service.unit.feature.FeatureService;
import com.AlTaraf.Booking.service.unit.statusUnit.StatusUnitService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    UnitService unitService;

    @Autowired
    StatusUnitService statusUnitService;

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

    @Autowired
    EventHallsMapper eventHallsMapper;

    @Autowired
    UnitResidenciesResponseMapper unitResidenciesResponseMapper;

    @Autowired
    private RoomDetailsRequestMapper roomDetailsRequestMapper;

    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private UnitGeneralResponseMapper unitGeneralResponseMapper;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    private RoomAvailableService roomAvailableService;
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);


    @PostMapping("/create-unit")
    public ResponseEntity<?> createUnit(@RequestBody UnitRequestDto unitRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Unit unitToSave = unitRequestMapper.toUnit(unitRequestDto);

            // Save the unit in the database
            Unit savedUnit = unitService.saveUnit(unitToSave);

            // Return the unitId in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body("Unit created successfully with id: " + savedUnit.getId());
        } catch (Exception e) {
//            // Log the exception
//            logger.error("Error occurred while processing create-unit request", e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to create unit. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

//    @GetMapping("/get-Units-By-Hotel-Classification-Names")
//    public ResponseEntity<?> getUnitsByHotelClassificationNames(
//            @RequestParam List<String> hotelClassificationNames,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "2") int size) {
//
//        Page<UnitDtoFavorite> units = unitService.getUnitsByHotelClassificationNames(hotelClassificationNames, page, size);
//
//        if (!units.isEmpty()) {
//            return new ResponseEntity<>(units, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content for Units have high classification!");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

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
    public ResponseEntity<?> getUnitsByUserCity(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<UnitDtoFavorite> units = unitService.getUnitsByUserCity(userId, PageRequest.of(page, size));

        if (units.hasContent()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units By User City!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @GetMapping("general-units/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitGeneralResponseDto responseDto = unitGeneralResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }

    @GetMapping("Residencies-units/{id}")
    public ResponseEntity<?> getResidenciesUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitResidenciesResponseDto responseDto = unitResidenciesResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }

    @PostMapping("{unitId}/{roomAvailableId}/room-details/add")
    @Transactional // Add this annotation to enable transaction management
    public ResponseEntity<?> addRoomDetails(@PathVariable Long unitId, @PathVariable Long roomAvailableId, @RequestBody RoomDetailsRequestDto roomDetailsRequestDto) {
        try {
            RoomDetails roomDetails = roomDetailsRequestMapper.toEntity(roomDetailsRequestDto);
            roomDetailsService.addRoomDetails(unitId,roomAvailableId, roomDetails);
            return ResponseEntity.ok("RoomDetails added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetails: " + e.getMessage());
        }
    }

    @GetMapping("/getByUnitAndRoomAvailable")
    public ResponseEntity<?> getRoomDetailsByUnitAndRoomAvailable(
            @RequestParam Long unitId,
            @RequestParam Long roomAvailableId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);

            // Map RoomDetails entity to RoomDetailsResponseDto
            RoomDetailsRequestDto roomDetailsResponseDto = roomDetailsRequestMapper.toDto(roomDetails);

            // Return the response
            return ResponseEntity.ok(roomDetailsResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @PatchMapping("/update-room-details")
//    public ResponseEntity<?> updateRoomDetailsForUnit(@RequestParam("unitId") Long unitId) {
//        unitService.updateImageDataUnit(unitId);
//        return new ResponseEntity<>("ImageData entities updated for Unit with ID: " + unitId, HttpStatus.OK);
//    }

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

//        @GetMapping("unit/{id}")
//    public Unit getUnitById(@PathVariable Long id) {
//        return unitService.getUnitById(id);
//    }

    @GetMapping
    public Page<UnitDtoFavorite> getAllUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return unitService.getAllUnitDtoFavorites(PageRequest.of(page, size));
    }

    @GetMapping("/filter-unit-by-name")
    public Page<UnitDtoFavorite> filterUnitsByName(
            @RequestParam String nameUnit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Unit> unitsPage = unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size));
        return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
    }

    @GetMapping("/get-All-Room-Available")
    public ResponseEntity<List<RoomAvailable>> getAllRoomAvailable() {
        List<RoomAvailable> roomAvailableList = roomAvailableService.getAllRoomAvailable();
        return ResponseEntity.ok(roomAvailableList);
    }

    @GetMapping("/get-All-Feature-For-Halls")
    public ResponseEntity<?> getAllFeatureForHalls() {
        List<FeatureForHalls> featureForHalls = featureForHallsService.getAllFeatureForHalls();

        if (!featureForHalls.isEmpty()) {
            return new ResponseEntity<>(featureForHalls, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }
    }

    @GetMapping("/get-AvailablePeriods")
    public ResponseEntity<?> getAllAvailablePeriods() {
        List<AvailablePeriods> availablePeriods = availablePeriodsService.getAllAvailablePeriods();

        if (!availablePeriods.isEmpty()) {
            return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }
    }

    @GetMapping("/byUnitType/{unitTypeId}")
    public ResponseEntity<?> getUnitsByUnitType(@PathVariable Long unitTypeId) {
        List<Unit> units = unitService.getUnitsByUnitTypeId(unitTypeId);

        if (units.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        } else {
            List<UnitDtoFavorite> unitDtoFavorites = unitFavoriteMapper.toUnitFavoriteDtoList(units);
            return ResponseEntity.ok(unitDtoFavorites);
        }
    }

    @GetMapping("Event-Halls-units/{unitId}")
    public ResponseEntity<?> getEventHallsById(@PathVariable Long unitId) {
        Unit unit = unitService.getUnitById(unitId);
        if (unit == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }

        EventHallsResponse eventHallsResponse = eventHallsMapper.toEventHallsResponse(unit);
        return ResponseEntity.ok(eventHallsResponse);
    }


    @GetMapping("/filter-event-halls")
    public ResponseEntity<?> searchUnits(
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long availablePeriodId,
            @RequestParam(required = false) Integer newPriceHall
    ) {
        try {
            List<Unit> units = unitService.findUnitsByCriteria(cityId, regionId, availablePeriodId, newPriceHall);
            List<EventHallsResponse> unitResponses = eventHallsMapper.toEventHallsList(units);
            return ResponseEntity.ok(unitResponses);
        } catch (Exception e) {
            logger.error("Error occurred while processing create-unit request", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(204, "No Content for Event Halls!"));
        }
    }

    @GetMapping("/units/filter")
    public List<Unit> filterUnits(
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long availablePeriodsId,
            @RequestParam(required = false, defaultValue = "0") int newPriceHall,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(required = false) Long accommodationTypeId,
            @RequestParam(required = false) Long hotelClassificationId,
            @RequestParam(required = false) Set<Long> basicFeaturesIds,
            @RequestParam(required = false) Set<Long> subFeaturesIds,
            @RequestParam(required = false) Set<Long> foodOptionsIds,
            @RequestParam(required = false, defaultValue = "0") int adultsAllowed,
            @RequestParam(required = false, defaultValue = "0") int childrenAllowed) {

        return unitService.findUnitsByFilters(cityId, regionId, availablePeriodsId, newPriceHall,
                unitTypeId, accommodationTypeId, hotelClassificationId,
                basicFeaturesIds, subFeaturesIds, foodOptionsIds, adultsAllowed, childrenAllowed
                );
    }


}
