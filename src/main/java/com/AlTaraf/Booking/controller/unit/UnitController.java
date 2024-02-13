package com.AlTaraf.Booking.controller.unit;

import com.AlTaraf.Booking.dto.Unit.UnitDto;
import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.cityAndregion.Region;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.entity.unit.unitType.UnitType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PatchMapping("/update-unit/{unitId}")
    public ResponseEntity<?> updateUnit(@PathVariable Long unitId, @RequestBody UnitRequestDto unitRequestDto) {
        try {
            // Find the unit to update
            Unit unitToUpdate = unitService.getUnitById(unitId);
            if (unitToUpdate == null) {
                // Return a 404 Not Found response if the unit does not exist
                return ResponseEntity.notFound().build();
            }

            // Update the unit fields
//            if (unitRequestDto.getId() != null) {
//                unitToUpdate.setId(unitRequestDto.getId());
//            }
            if (unitRequestDto.getUnitTypeId() != null) {
                unitToUpdate.setUnitType(new UnitType(unitRequestDto.getUnitTypeId()));
            }
            if (unitRequestDto.getUserId() != null) {
                unitToUpdate.setUser(new User(unitRequestDto.getUserId()));
            }
            if (unitRequestDto.getNameUnit() != null) {
                unitToUpdate.setNameUnit(unitRequestDto.getNameUnit());
            }
            if (unitRequestDto.getDescription() != null) {
                unitToUpdate.setDescription(unitRequestDto.getDescription());
            }
            if (unitRequestDto.getCityId() != null) {
                unitToUpdate.setCity(new City(unitRequestDto.getCityId()));
            }
            if (unitRequestDto.getRegionId() != null) {
                unitToUpdate.setRegion(new Region(unitRequestDto.getRegionId()));
            }
            if (unitRequestDto.getAccommodationTypeId() != null) {
                unitToUpdate.setAccommodationType(new AccommodationType(unitRequestDto.getAccommodationTypeId()));
            }
            if (unitRequestDto.getHotelClassificationId() != null) {
                unitToUpdate.setHotelClassification(new HotelClassification(unitRequestDto.getHotelClassificationId()));
            }
            if (unitRequestDto.getRoomAvailableIds() != null) {
                unitToUpdate.setRoomAvailableSet(unitRequestDto.getRoomAvailableIds().stream()
                        .map(id -> new RoomAvailable(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getAvailableAreaIds() != null) {
                unitToUpdate.setAvailableAreaSet(unitRequestDto.getAvailableAreaIds().stream()
                        .map(id -> new AvailableArea(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getBasicFeaturesIds() != null) {
                unitToUpdate.setBasicFeaturesSet(unitRequestDto.getBasicFeaturesIds().stream()
                        .map(id -> new Feature(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getSubFeaturesIds() != null) {
                unitToUpdate.setSubFeaturesSet(unitRequestDto.getSubFeaturesIds().stream()
                        .map(id -> new SubFeature(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getFoodOptionsIds() != null) {
                unitToUpdate.setFoodOptionsSet(unitRequestDto.getFoodOptionsIds().stream()
                        .map(id -> new FoodOption(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getCapacityHalls() != 0) {
                unitToUpdate.setCapacityHalls(unitRequestDto.getCapacityHalls());
            }
            if (unitRequestDto.getFeaturesHallsIds() != null) {
                unitToUpdate.setFeaturesHallsSet(unitRequestDto.getFeaturesHallsIds().stream()
                        .map(id -> new FeatureForHalls(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getAvailablePeriodsHallsIds() != null) {
                unitToUpdate.setAvailablePeriodsHallsSet(unitRequestDto.getAvailablePeriodsHallsIds().stream()
                        .map(id -> new AvailablePeriods(id))
                        .collect(Collectors.toSet()));
            }
            if (unitRequestDto.getOldPriceHall() != 0) {
                unitToUpdate.setOldPriceHall(unitRequestDto.getOldPriceHall());
            }
            if (unitRequestDto.getNewPriceHall() != 0) {
                unitToUpdate.setNewPriceHall(unitRequestDto.getNewPriceHall());
            }
            if (unitRequestDto.getLatForMapping() != null) {
                unitToUpdate.setLatForMapping(unitRequestDto.getLatForMapping());
            }
            if (unitRequestDto.getLongForMapping() != null) {
                unitToUpdate.setLongForMapping(unitRequestDto.getLongForMapping());
            }
            // Update other fields similarly...

            // Save the updated unit in the database
            Unit updatedUnit = unitService.saveUnit(unitToUpdate);

            // Return a success response with the updated unitId in the body
            return ResponseEntity.ok("Unit updated successfully with id: " + updatedUnit.getId());
        } catch (Exception e) {
            // Log the exception
            // logger.error("Error occurred while processing update-unit request", e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to update unit. Please check your input and try again.");
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

    @GetMapping("/get-Units")
    public Page<UnitDtoFavorite> getUnits(
            @RequestParam(required = false) String nameUnit,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Unit> unitsPage = Page.empty();

        if (nameUnit == null && unitTypeId != null) {
            unitsPage = unitService.getUnitsByUnitTypeId(unitTypeId, PageRequest.of(page, size));
        }
        else if (nameUnit != null && unitTypeId == null) {
            unitsPage = unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size));
        }
        else if (nameUnit != null && unitTypeId != null) {
            unitsPage = unitService.filterUnitsByNameAndTypeId(nameUnit, unitTypeId, PageRequest.of(page, size));
        }
        else if (nameUnit == null && unitTypeId == null) {
            unitsPage = unitService.getAllUnit(PageRequest.of(page, size));
        }
        return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
    }

    @GetMapping("/Filter-Units-For-Map")
    public List<UnitDtoFavorite> filterUnitsForMap(
            @RequestParam(required = false) String nameUnit,
            @RequestParam(required = false) Long unitTypeId) {
        List<Unit> units = new ArrayList<>();

        if (nameUnit == null && unitTypeId != null) {
            units = unitService.getUnitTypeIdForMap(unitTypeId);
        }
        else if (nameUnit != null && unitTypeId == null) {
            units = unitService.filterUnitsByNameForMap(nameUnit);
        }
        else if (nameUnit != null && unitTypeId != null) {
            units = unitService.filterUnitsByNameAndTypeIdForMap(nameUnit, unitTypeId);
        }
        else if (nameUnit == null && unitTypeId == null) {
            units = unitService.getAllUnitForMap();
        }
        return units.stream()
                .map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit))
                .collect(Collectors.toList());
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

    @GetMapping("Event-Halls-units/{unitId}")
    public ResponseEntity<?> getEventHallsById(@PathVariable Long unitId) {
        Unit unit = unitService.getUnitById(unitId);
        if (unit == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }

        EventHallsResponse eventHallsResponse = eventHallsMapper.toEventHallsResponse(unit);
        return ResponseEntity.ok(eventHallsResponse);
    }

    @GetMapping("/units/filter")
    public ResponseEntity<?> filterUnits(
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

        try {
            List<Unit> units = unitService.findUnitsByFilters(cityId, regionId, availablePeriodsId, newPriceHall,
                    unitTypeId, accommodationTypeId, hotelClassificationId,
                    basicFeaturesIds, subFeaturesIds, foodOptionsIds, adultsAllowed, childrenAllowed);

            List<UnitDtoFavorite>  unitFavoriteDtoList = unitFavoriteMapper.toUnitFavoriteDtoList(units);
            return ResponseEntity.ok(unitFavoriteDtoList);

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // You can throw a custom exception or return an error response here if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(204, "No Content for Event Halls!"));
        }
    }


}
