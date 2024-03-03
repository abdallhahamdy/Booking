package com.AlTaraf.Booking.Controller.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDto;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationStatusMapper;
import com.AlTaraf.Booking.Mapper.Unit.*;
import com.AlTaraf.Booking.Mapper.Unit.RoomDetails.RoomDetailsRequestMapper;
import com.AlTaraf.Booking.Mapper.Unit.RoomDetails.RoomDetailsResponseMapper;
import com.AlTaraf.Booking.Payload.request.RoomDetails.RoomDetailsRequestDto;
import com.AlTaraf.Booking.Payload.request.UnitRequestDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationStatus;
import com.AlTaraf.Booking.Payload.response.RoomDetails.RoomDetailsResponseDto;
import com.AlTaraf.Booking.Payload.response.Unit.EventHallsResponse;
import com.AlTaraf.Booking.Payload.response.Unit.UnitGeneralResponseDto;
import com.AlTaraf.Booking.Payload.response.Unit.UnitResidenciesResponseDto;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.unit.AvailablePeriods.AvailablePeriodsService;
import com.AlTaraf.Booking.Service.unit.FeatureForHalls.FeatureForHallsService;
import com.AlTaraf.Booking.Service.unit.RoomAvailable.RoomAvailableService;
import com.AlTaraf.Booking.Service.unit.RoomDetails.RoomDetailsService;
import com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea.RoomDetailsForAvailableAreaService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.unit.feature.FeatureService;
import com.AlTaraf.Booking.Service.unit.statusUnit.StatusUnitService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Api/Units")
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
    private RoomDetailsResponseMapper roomDetailsResponseMapper;

    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private RoomDetailsForAvailableAreaService roomDetailsForAvailableAreaService;

    @Autowired
    private UnitGeneralResponseMapper unitGeneralResponseMapper;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    private RoomAvailableService roomAvailableService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    ReservationStatusMapper reservationStatusMapper;

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);


    @PostMapping("/Create-Unit")
    public ResponseEntity<?> createUnit(@RequestBody UnitRequestDto unitRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Unit unitToSave = unitRequestMapper.toUnit(unitRequestDto);

            // Set default value for newPriceHall if it's null
            if (unitToSave.getNewPriceHall() == null) {
                unitToSave.setNewPriceHall(0); // Or set it to some default value
            }

            if (unitToSave.getOldPriceHall() == null) {
                unitToSave.setOldPriceHall(0); // Set oldPriceHall to 0 if it's null
            }

            // Check if newPriceHall is less than oldPriceHall
//            if (unitToSave.getNewPriceHall() != null &&
//                    unitToSave.getNewPriceHall() != 0 &&
//                    unitToSave.getOldPriceHall() != null &&
//                    unitToSave.getNewPriceHall() >= unitToSave.getOldPriceHall()) {
//                throw new IllegalArgumentException("New price must be less than old price.");
//            }



            if (unitToSave.getResortNewPrice() == null) {
                unitToSave.setResortNewPrice(0); // Or set it to some default value
            }

            if (unitToSave.getResortOldPrice() == null) {
                unitToSave.setResortOldPrice(0); // Set oldPriceHall to 0 if it's null
            }

            // Check if newPriceHall is less than oldPriceHall
//            if (unitToSave.getResortNewPrice() != null &&
//                    unitToSave.getResortNewPrice() != 0 &&
//                    unitToSave.getResortOldPrice() != null &&
//                    unitToSave.getResortOldPrice() >= unitToSave.getResortOldPrice()) {
//                throw new IllegalArgumentException("New price must be less than old price.");
//            }



            if (unitToSave.getChaletNewPrice() == null) {
                unitToSave.setChaletNewPrice(0); // Or set it to some default value
            }

            if (unitToSave.getChaletOldPrice() == null) {
                unitToSave.setChaletOldPrice(0); // Set oldPriceHall to 0 if it's null
            }

            if (unitToSave.getLoungeNewPrice() == null) {
                unitToSave.setLoungeNewPrice(0); // Or set it to some default value
            }

            if (unitToSave.getLoungeOldPrice() == null) {
                unitToSave.setLoungeOldPrice(0); // Set oldPriceHall to 0 if it's null
            }

            // Check if newPriceHall is less than oldPriceHall
//            if (unitToSave.getChaletNewPrice() != null &&
//                    unitToSave.getChaletNewPrice() != 0 &&
//                    unitToSave.getChaletOldPrice() != null &&
//                    unitToSave.getChaletOldPrice() >= unitToSave.getChaletOldPrice()) {
//                throw new IllegalArgumentException("New price must be less than old price.");
//            }

            // Calculate the price based on the unitType
            unitToSave.calculatePrice();

            // Save the unit in the database
            Unit savedUnit = unitService.saveUnit(unitToSave);

            // Return the unitId in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body("Unit created successfully with id: " + savedUnit.getId());
        } catch (IllegalArgumentException e) {
            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return generic error response
            ApiResponse response = new ApiResponse(400, "Failed to create unit. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PatchMapping("/Update-Unit/{unitId}")
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
                System.out.println("Named Unit: " + unitRequestDto.getNameUnit());
                unitToUpdate.setNameUnit(unitRequestDto.getNameUnit());
                System.out.println("After Named Unit: " + unitRequestDto.getNameUnit());
            }

            if (unitRequestDto.getDescription() != null) {
                unitToUpdate.setDescription(unitRequestDto.getDescription());
                System.out.println("After Update Description: " + unitRequestDto.getDescription());
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
//            if (unitRequestDto.getSubFeaturesIds() != null) {
//                unitToUpdate.setSubFeaturesSet(unitRequestDto.getSubFeaturesIds().stream()
//                        .map(id -> new SubFeature(id))
//                        .collect(Collectors.toSet()));
//            }
//            if (unitRequestDto.getFoodOptionsIds() != null) {
//                unitToUpdate.setFoodOptionsSet(unitRequestDto.getFoodOptionsIds().stream()
//                        .map(id -> new FoodOption(id))
//                        .collect(Collectors.toSet()));
//            }
//            if (unitRequestDto.getCapacityHalls() != 0) {
//                unitToUpdate.setCapacityHalls(unitRequestDto.getCapacityHalls());
//            }
//            if (unitRequestDto.getFeaturesHallsIds() != null) {
//                unitToUpdate.setFeaturesHallsSet(unitRequestDto.getFeaturesHallsIds().stream()
//                        .map(id -> new FeatureForHalls(id))
//                        .collect(Collectors.toSet()));
//            }
//            if (unitRequestDto.getAvailablePeriodsHallsIds() != null) {
//                unitToUpdate.setAvailablePeriodsHallsSet(unitRequestDto.getAvailablePeriodsHallsIds().stream()
//                        .map(id -> new AvailablePeriods(id))
//                        .collect(Collectors.toSet()));
//            }
//
//            if (unitRequestDto.getOldPriceHall() != 0) {
//                unitToUpdate.setOldPriceHall(unitRequestDto.getOldPriceHall());
//            }
//            if (unitRequestDto.getNewPriceHall() != 0) {
//                unitToUpdate.setNewPriceHall(unitRequestDto.getNewPriceHall());
//            }
//
//
//
//            if (unitRequestDto.getChaletOldPrice() != 0) {
//                unitToUpdate.setChaletOldPrice(unitRequestDto.getChaletOldPrice());
//            }
//            if (unitRequestDto.getChaletNewPrice() != 0) {
//                unitToUpdate.setChaletNewPrice(unitRequestDto.getChaletNewPrice());
//            }
//
//            if (unitRequestDto.getResortOldPrice() != 0) {
//                unitToUpdate.setResortOldPrice(unitRequestDto.getResortOldPrice());
//            }
//            if (unitRequestDto.getResortNewPrice() != 0) {
//                unitToUpdate.setResortNewPrice(unitRequestDto.getResortNewPrice());
//            }


//            if (unitRequestDto.getLatForMapping() != null) {
//                unitToUpdate.setLatForMapping(unitRequestDto.getLatForMapping());
//            }
//            if (unitRequestDto.getLongForMapping() != null) {
//                unitToUpdate.setLongForMapping(unitRequestDto.getLongForMapping());
//            }
            // Update other fields similarly...

            // Save the updated unit in the database
            Unit updatedUnit = unitService.saveUnit(unitToUpdate);

            // Return a success response with the updated unitId in the body
            return ResponseEntity.ok("Unit updated successfully with id: " + updatedUnit.getId());
        } catch (Exception e) {
            // Log the exception
            // logger.error("Error occurred while processing update-unit request", e);

            System.out.println("Exception for Update Unit: " + e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to update unit. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get-units-by-evaluation")
    public ResponseEntity<?> getUnitsByEvaluationNames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        try {
            Page<UnitDtoFavorite> units = unitService.getUnitByEvaluationInOrderByEvaluationScoreDesc(page, size);

            if (!units.isEmpty()) {
                return new ResponseEntity<>(units, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(204, "No Content for Units have high evaluation!");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/By-Last-Month")
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

    @GetMapping("/Get-Units-By-Accommodation-Type")
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

    @GetMapping("/By-User-City")
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

    @GetMapping("By-Id-General/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitGeneralResponseDto responseDto = unitGeneralResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }

    @GetMapping("By-Id-For-Residencies/{id}")
    public ResponseEntity<?> getResidenciesUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitResidenciesResponseDto responseDto = unitResidenciesResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }

    @GetMapping("Event-Halls-Units/{unitId}")
    public ResponseEntity<?> getEventHallsById(@PathVariable Long unitId) {
        Unit unit = unitService.getUnitById(unitId);
        if (unit == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }

        EventHallsResponse eventHallsResponse = eventHallsMapper.toEventHallsResponse(unit);
        return ResponseEntity.ok(eventHallsResponse);
    }

    @PostMapping("{unitId}/{roomAvailableId}/Room-Details/Add")
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

    @PostMapping("{unitId}/{availableAreaId}/Room-Details-For-Available-Area/Add")
    @Transactional // Add this annotation to enable transaction management
    public ResponseEntity<?> addRoomDetailsForAvailableArea(@PathVariable Long unitId, @PathVariable Long availableAreaId, @RequestBody RoomDetailsRequestDto roomDetailsRequestDto) {
        try {
            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsRequestMapper.toEntityAvailableArea(roomDetailsRequestDto);
            roomDetailsForAvailableAreaService.addRoomDetails(unitId,availableAreaId, roomDetailsForAvailableArea);
            return ResponseEntity.ok("RoomDetails added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetails: " + e.getMessage());
        }
    }

    @GetMapping("/Get-By-Unit-And-Room-Available")
    public ResponseEntity<?> getRoomDetailsByUnitAndRoomAvailable(
            @RequestParam Long unitId,
            @RequestParam Long roomAvailableId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);

            // Map RoomDetails entity to RoomDetailsResponseDto
            RoomDetailsResponseDto roomDetailsResponseDto = roomDetailsResponseMapper.toDto(roomDetails);

            // Return the response
            return ResponseEntity.ok(roomDetailsResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/Get-By-Unit-And-Available-Area")
    public ResponseEntity<?> getRoomDetailsByUnitAndAvailableArea(
            @RequestParam Long unitId,
            @RequestParam Long availableAreaId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetailsForAvailableArea availableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(unitId, availableAreaId);

            // Map RoomDetails entity to RoomDetailsResponseDto
            RoomDetailsResponseDto roomDetailsResponseDto = roomDetailsResponseMapper.toDtoForAvailableArea(availableArea);

            // Return the response
            return ResponseEntity.ok(roomDetailsResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/Status-Unit/Unit")
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

    @DeleteMapping("Delete/Unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {

        try {
            unitService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Unit deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            System.out.println("Error Message Delete Unit: " + e);
        ApiResponse response = new ApiResponse(404, "Not Found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

//        @GetMapping("unit/{id}")
//    public Unit getUnitById(@PathVariable Long id) {
//        return unitService.getUnitById(id);
//    }

    @GetMapping("/Get-Units")
    public Page<UnitDtoFavorite> getUnits(
            @RequestParam(required = false) String nameUnit,
            @RequestParam(required = false) String roomAvailableName,
            @RequestParam(required = false) String availableAreaName,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
        Page<Unit> unitsPage = Page.empty();

        if (nameUnit == null && unitTypeId != null) {
            unitsPage = unitService.getUnitsByUnitTypeId(unitTypeId, PageRequest.of(page, size, sort));
        }
        else if (nameUnit != null && unitTypeId == null) {
            unitsPage = unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size, sort));
        }
        else if (nameUnit != null && unitTypeId != null) {
            unitsPage = unitService.filterUnitsByNameAndTypeId(nameUnit, unitTypeId, PageRequest.of(page, size, sort));
        }
        else if (nameUnit == null && unitTypeId == null && roomAvailableName != null) {
            unitsPage = unitService.filterUnitsByRoomAvailableName(roomAvailableName, PageRequest.of(page, size, sort));
        }
        else if (nameUnit != null && unitTypeId == null && roomAvailableName != null) {
            unitsPage = unitService.findByNameUnitAndRoomAvailableNameContainingIgnoreCase(nameUnit, roomAvailableName, PageRequest.of(page, size, sort));
        }
        else if (nameUnit == null && unitTypeId == null && roomAvailableName == null && availableAreaName != null) {
            unitsPage = unitService.filterUnitsByAvailableAreaName(availableAreaName, PageRequest.of(page, size, sort));
        }
        else if (nameUnit != null && unitTypeId == null && roomAvailableName != null && availableAreaName != null) {
            unitsPage = unitService.findByNameUnitAndAvailableAreaNameContainingIgnoreCase(nameUnit, availableAreaName, PageRequest.of(page, size, sort));
        }
        else if (nameUnit == null && unitTypeId == null && roomAvailableName == null && availableAreaName == null) {
            unitsPage = unitService.getAllUnit(PageRequest.of(page, size, sort));
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

    @GetMapping("/Get-All-Room-Available")
    public ResponseEntity<List<RoomAvailable>> getAllRoomAvailable() {
        List<RoomAvailable> roomAvailableList = roomAvailableService.getAllRoomAvailable();
        return ResponseEntity.ok(roomAvailableList);
    }

    @GetMapping("/Get-All-Feature-For-Halls")
    public ResponseEntity<?> getAllFeatureForHalls() {
        List<FeatureForHalls> featureForHalls = featureForHallsService.getAllFeatureForHalls();

        if (!featureForHalls.isEmpty()) {
            return new ResponseEntity<>(featureForHalls, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }
    }

    @GetMapping("/Get-Available-Periods")
    public ResponseEntity<?> getAllAvailablePeriods() {
        List<AvailablePeriods> availablePeriods = availablePeriodsService.getAllAvailablePeriods();

        if (!availablePeriods.isEmpty()) {
            return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Available Periods !"));
        }
    }

    @GetMapping("/Filtering")
    public ResponseEntity<?> filterUnits(
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long availablePeriodsId,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(required = false) Long accommodationTypeId,
            @RequestParam(required = false) Set<Long> hotelClassificationIds,
            @RequestParam(required = false) Set<Long> basicFeaturesIds,
            @RequestParam(required = false) Set<Long> subFeaturesIds,
            @RequestParam(required = false) Set<Long> foodOptionsIds,
            @RequestParam(required = false) Set<Long> evaluationId,
            @RequestParam(required = false, defaultValue = "0") int capacityHalls,
            @RequestParam(required = false, defaultValue = "0") int adultsAllowed,
            @RequestParam(required = false, defaultValue = "0") int childrenAllowed,
            @RequestParam(required = false, defaultValue = "0") int priceMin,
            @RequestParam(required = false, defaultValue = "0") int priceMax) {

        try {
            List<Unit> units = unitService.findUnitsByFilters(cityId, regionId, availablePeriodsId,
                    unitTypeId, accommodationTypeId, hotelClassificationIds,
                    basicFeaturesIds, subFeaturesIds, foodOptionsIds, evaluationId, capacityHalls, adultsAllowed, childrenAllowed,
                    priceMin, priceMax);

            List<UnitDtoFavorite>  unitFavoriteDtoList = unitFavoriteMapper.toUnitFavoriteDtoList(units);
            return ResponseEntity.ok(unitFavoriteDtoList);

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // You can throw a custom exception or return an error response here if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(204, "No Content for Event Halls!"));
        }
    }

    @PutMapping("Change/Status/Units/{reservationId}/{statusUnitId}")
    public ResponseEntity<?> updateStatusForReservations(@PathVariable Long reservationId, @PathVariable Long statusUnitId) {
        try {

//            AvailableArea availableArea = reservationService.getAvailableAreaByReservations(reservationId);
//            System.out.println("Available Area: " + availableArea.getId());

            reservationService.updateStatusForReservation(reservationId, statusUnitId);
            return ResponseEntity.ok(new ApiResponse(200,"Status changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @GetMapping("/Get-Reservation-By-Status")
    public ResponseEntity<?> getReservationByStatus(@RequestParam(name = "USER_ID") Long userId,
                                                    @RequestParam(name = "statusUnitName") String statusUnitName,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, pageable);

        if (unitsPage == null || unitsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content"));
        }

        List<ReservationStatus> reservationRequestDtoList = new ArrayList<>();

        for (Unit unit : unitsPage.getContent()) {
            System.out.println("unit: " + unit.getId());
            Page<Reservations> reservationsPage = reservationService.findByStatusNameAndUnitId(statusUnitName, unit.getId(), pageable);
            reservationRequestDtoList.addAll(reservationStatusMapper.toReservationStatusDtoList(reservationsPage.getContent()));
        }

        if (reservationRequestDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content"));
        }

        return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
    }

    @DeleteMapping("Delete/Reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {

        try {
            reservationService.updateStatusForReservation(id, 4L);
//            reservationService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Reservation deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
