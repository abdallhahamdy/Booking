package com.AlTaraf.Booking.Controller.unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDto;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.Image.ImageData;
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
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
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
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.roomAvailable.RoomAvailableRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.*;
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
    private AdsRepository adsRepository;

    @Autowired
    ReservationStatusMapper reservationStatusMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private RoomAvailableRepository roomAvailableRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    private ReserveDateRepository reserveDateRepository;

    @Autowired
    private ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private UserFavoriteUnitRepository userFavoriteUnitRepository;


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

            if (unitToSave.getResortNewPrice() == null) {
                unitToSave.setResortNewPrice(0); // Or set it to some default value
            }

            if (unitToSave.getResortOldPrice() == null) {
                unitToSave.setResortOldPrice(0); // Set oldPriceHall to 0 if it's null
            }

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

        Sort sort = Sort.by("id").ascending();
        Page<UnitDtoFavorite> units = unitService.getUnitsAddedLastMonth(page, size, sort);

        if (!units.isEmpty()) {
            return new ResponseEntity<>(units, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @GetMapping("/Get-Units-By-Accommodation-Type")
    public ResponseEntity<?> getUnitsByAccommodationType(
            @RequestParam Long accommodationTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Sort sort = Sort.by("id").ascending(); // Sort by unit ID in ascending order

        Page<UnitDtoFavorite> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeId, page, size, sort);

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

        Sort sort = Sort.by("id").ascending(); // Sort by unit ID in ascending order

        Page<UnitDtoFavorite> units = unitService.getUnitsByUserCity(userId, PageRequest.of(page, size), sort);

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
            // Check if RoomDetails already exists for the given unitId and roomAvailableId
            boolean roomDetailsExists = roomDetailsRepository.existsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);
            if (roomDetailsExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"RoomDetails already exists for unitId: " + unitId + " and roomAvailableId: " + roomAvailableId));
            }


            RoomDetails roomDetails = roomDetailsRequestMapper.toEntity(roomDetailsRequestDto);
            roomDetailsService.addRoomDetails(unitId, roomAvailableId, roomDetails);
            return ResponseEntity.ok("RoomDetails added successfully " + roomDetails.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetails: " + e.getMessage());
        }
    }

    @PostMapping("{unitId}/{availableAreaId}/Room-Details-For-Available-Area/Add")
    @Transactional // Add this annotation to enable transaction management
    public ResponseEntity<?> addRoomDetailsForAvailableArea(@PathVariable Long unitId, @PathVariable Long availableAreaId, @RequestBody RoomDetailsRequestDto roomDetailsRequestDto) {
        try {
            // Check if RoomDetailsForAvailableArea already exists for the given unitId and availableAreaId
            boolean roomDetailsExists = roomDetailsForAvailableAreaRepository.existsByUnitIdAndAvailableAreaId(unitId, availableAreaId);
            if (roomDetailsExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"RoomDetailsForAvailableArea already exists for unitId: " + unitId + " and availableAreaId: " + availableAreaId));
            }

            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsRequestMapper.toEntityAvailableArea(roomDetailsRequestDto);
            roomDetailsForAvailableAreaService.addRoomDetails(unitId, availableAreaId, roomDetailsForAvailableArea);
            return ResponseEntity.ok("RoomDetailsForAvailableArea added successfully " + roomDetailsForAvailableArea.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetailsForAvailableArea: " + e.getMessage());
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
            @RequestParam(name = "statusUnitName") String statusUnitName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Unit> unitPage = unitService.getUnitsForUserAndStatus(userId, statusUnitName, pageable);

        if (unitPage.hasContent()) {
            List<UnitDto> unitDtos = unitMapper.toUnitDtoList(unitPage.getContent());
            return new ResponseEntity<>(unitDtos, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @Transactional
    @DeleteMapping("Delete/Unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        try {
            // Check if the unit is associated with any reservations
            if (reservationRepository.existsByUnitId(id)) {
                ApiResponse response = new ApiResponse(400, "Unit is associated with reservations and cannot be deleted!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            userFavoriteUnitRepository.deleteByUnit(id);
            roomDetailsForAvailableAreaRepository.deleteByUnitId(id);
            reserveDateRepository.deleteByUnitId(id);
            reserveDateHallsRepository.deleteRelatedDateInfoHallsByUnitId(id);
            reserveDateHallsRepository.deleteByUnitId(id);
            imageDataRepository.deleteByUnitId(id);
            adsRepository.deleteByUnitId(id);

            unitService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Unit deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            System.out.println("Error Message Delete Unit: " + e);
            // Rethrow the exception to mark the transaction as rollback-only
            throw e;
        }
    }


    private Sort getDefaultSort() {
        return Sort.by("price").ascending();
    }

    private Sort getSortByPrice(String sortDirection) {
        return sortDirection.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
    }

    private List<UnitDtoFavorite> sortByLocation(List<UnitDtoFavorite> units, Double userLat, Double userLong, String sortDirection) {
        units.sort(Comparator.comparingDouble(unit -> {
            double latDiff = userLat - unit.getLatForMapping();
            double longDiff = userLong - unit.getLongForMapping();
            return Math.sqrt(latDiff * latDiff + longDiff * longDiff);
        }));

        if (sortDirection.equalsIgnoreCase("desc")) {
            Collections.reverse(units);
        }

        return units;
    }

    private Sort getSortByEvaluationId(String sortDirection) {
        return sortDirection.equalsIgnoreCase("desc") ? Sort.by("evaluation.id").descending() : Sort.by("evaluation.id").ascending();
    }

    @GetMapping("/Get-Units")
    public Page<UnitDtoFavorite> getUnits(
            @RequestParam(required = false) String nameUnit,
            @RequestParam(required = false) String roomAvailableName,
            @RequestParam(required = false) String availableAreaName,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sortDirectionByPrice,
            @RequestParam(required = false) String sortDirectionByEvaluationId,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLng
    ) {
        Sort sort = Sort.unsorted();

        if (sortDirectionByPrice != null) {
            sort = getSortByPrice(sortDirectionByPrice);
        }

        if (sortDirectionByEvaluationId != null) {
            sort = sort.and(getSortByEvaluationId(sortDirectionByEvaluationId));
        }

        Page<Unit> unitsPage = Page.empty();

        if (nameUnit == null && unitTypeId != null) {
            unitsPage = unitService.getUnitsByUnitTypeId(unitTypeId, PageRequest.of(page, size, sort));
        } else if (nameUnit != null && unitTypeId == null) {
            unitsPage = unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size, sort));
        } else if (nameUnit != null && unitTypeId != null) {
            unitsPage = unitService.filterUnitsByNameAndTypeId(nameUnit, unitTypeId, PageRequest.of(page, size, sort));
        } else if (nameUnit == null && unitTypeId == null && roomAvailableName != null) {
            unitsPage = unitService.filterUnitsByRoomAvailableName(roomAvailableName, PageRequest.of(page, size, sort));
        } else if (nameUnit != null && unitTypeId == null && roomAvailableName != null) {
            unitsPage = unitService.findByNameUnitAndRoomAvailableNameContainingIgnoreCase(nameUnit, roomAvailableName, PageRequest.of(page, size, sort));
        } else if (nameUnit == null && unitTypeId == null && roomAvailableName == null && availableAreaName != null) {
            unitsPage = unitService.filterUnitsByAvailableAreaName(availableAreaName, PageRequest.of(page, size, sort));
        } else if (nameUnit != null && unitTypeId == null && roomAvailableName != null && availableAreaName != null) {
            unitsPage = unitService.findByNameUnitAndAvailableAreaNameContainingIgnoreCase(nameUnit, availableAreaName, PageRequest.of(page, size, sort));
        } else if (nameUnit == null && unitTypeId == null && roomAvailableName == null && availableAreaName == null) {
            unitsPage = unitService.getAllUnit(PageRequest.of(page, size, sort));
        }

        if (userLat != null && userLng != null) {
            List<UnitDtoFavorite> sortedUnits = unitsPage.stream()
                    .sorted(Comparator.comparingDouble(unit -> calculateDistance(userLat, userLng, unit.getLatForMapping(), unit.getLongForMapping())))
                    .map(unitFavoriteMapper::toUnitFavoriteDto)
                    .collect(Collectors.toList());
            return new PageImpl<>(sortedUnits, unitsPage.getPageable(), unitsPage.getTotalElements());
        }

        return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
    }

    private double calculateDistance(double userLat, double userLng, double unitLat, double unitLng) {
        double earthRadius = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(unitLat - userLat);
        double dLng = Math.toRadians(unitLng - userLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(unitLat)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
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
            reservationService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Reservation deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
