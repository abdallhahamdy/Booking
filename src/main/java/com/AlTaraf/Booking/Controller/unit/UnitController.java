package com.AlTaraf.Booking.Controller.unit;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
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
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
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
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.roomAvailable.RoomAvailableRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.UserFavoriteUnit.UserFavoriteUnitService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    private UserFavoriteUnitService userFavoriteUnitService;

    @Autowired
    StatusRepository statusUnitRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    private MessageSource messageSource;

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

//            if (unitToSave.getResortNewPrice() == null) {
//                unitToSave.setResortNewPrice(0); // Or set it to some default value
//            }
//
//            if (unitToSave.getResortOldPrice() == null) {
//                unitToSave.setResortOldPrice(0); // Set oldPriceHall to 0 if it's null
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

            // Calculate the price based on the unitType
            unitToSave.calculatePrice();


            Unit savedUnit = unitService.saveUnit(unitToSave);

            PushNotificationRequest notificationRequest = new PushNotificationRequest(messageSource.getMessage("notification_title.message", null, LocaleContextHolder.getLocale()),messageSource.getMessage("notification_body_units.message", null, LocaleContextHolder.getLocale()),unitRequestDto.getUserId());
            notificationService.processNotification(notificationRequest);

            // Return the unitId in the response body
//            return ResponseEntity.status(HttpStatus.CREATED).body("Successful_Add_Unit.message " + savedUnit.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("Successful_Add_Unit.message", null, LocaleContextHolder.getLocale()) + " " + savedUnit.getId());



        } catch (IllegalArgumentException e) {
            // Return user-friendly error response
//            ApiResponse response = new ApiResponse(400, e.getMessage());
            ApiResponse response = new ApiResponse(400,  messageSource.getMessage("Failed_Add_Unit.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return generic error response
//            ApiResponse response = new ApiResponse(400, "Failed_Add_Unit.message");
            ApiResponse response = new ApiResponse(400, messageSource.getMessage("Failed_Add_Unit.message", null, LocaleContextHolder.getLocale()));
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

            if (unitRequestDto.getCapacityHalls() != null) {
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

            if (unitRequestDto.getOldPriceHall() != null) {
                unitToUpdate.setOldPriceHall(unitRequestDto.getOldPriceHall());
            }

            if (unitRequestDto.getNewPriceHall() != null) {
                unitToUpdate.setNewPriceHall(unitRequestDto.getNewPriceHall());
            }

            if (unitRequestDto.getLatForMapping() != null) {
                unitToUpdate.setLatForMapping(unitRequestDto.getLatForMapping());
            }

            if (unitRequestDto.getLongForMapping() != null) {
                unitToUpdate.setLongForMapping(unitRequestDto.getLongForMapping());
            }

            if (unitRequestDto.getChaletNewPrice() != null) {
                unitToUpdate.setChaletNewPrice(unitRequestDto.getChaletNewPrice());
            }

            if (unitRequestDto.getChaletOldPrice() != null) {
                unitToUpdate.setChaletOldPrice(unitRequestDto.getChaletOldPrice());
            }

            if (unitRequestDto.getChildrenAllowed() != null) {
                unitToUpdate.setChildrenAllowed(unitRequestDto.getChildrenAllowed());
            }

            if (unitRequestDto.getAdultsAllowed() != null) {
                unitToUpdate.setAdultsAllowed(unitRequestDto.getAdultsAllowed());
            }

            if (unitRequestDto.getLoungeNewPrice() != null) {
                unitToUpdate.setLoungeNewPrice(unitRequestDto.getLoungeNewPrice());
            }

            if (unitRequestDto.getLoungeOldPrice() != null) {
                unitToUpdate.setLoungeOldPrice(unitRequestDto.getLoungeOldPrice());
            }


            // Update other fields similarly...

            // Save the updated unit in the database
            Unit updatedUnit = unitService.saveUnit(unitToUpdate);

            // Return a success response with the updated unitId in the body
            return ResponseEntity.ok( messageSource.getMessage("unit_updated.message", null, LocaleContextHolder.getLocale())
                    + " " + updatedUnit.getId());
//            return ResponseEntity.ok("Unit updated successfully with id: " + updatedUnit.getId());
        } catch (Exception e) {
            // Log the exception
            // logger.error("Error occurred while processing update-unit request", e);

            System.out.println("Exception for Update Unit: " + e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, messageSource.getMessage("failed.updated.message", null, LocaleContextHolder.getLocale()));
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
//                ApiResponse response = new ApiResponse(204, "No Content for Units have high evaluation!");
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/newly-added")
    public ResponseEntity<?> getUnitsAddedLastMonth() {

//        Sort sort = Sort.by("id").descending();
        try {
            List<UnitDtoFavorite> units = unitService.getNewlyAdded();

            if (!units.isEmpty()) {
                return new ResponseEntity<>(units, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        }  catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error occurred while processing get-units-by-evaluation request: " + e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/Get-Units-By-Accommodation-Type")
    public ResponseEntity<?> getUnitsByAccommodationType(
            @RequestParam Long accommodationTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        try {
            Sort sort = Sort.by("id").descending(); // Sort by unit ID in ascending order

            Page<UnitDtoFavorite> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeId, page, size, sort);

            if (!units.isEmpty()) {
                return new ResponseEntity<>(units, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/By-User-City")
    public ResponseEntity<?> getUnitsByUserCity(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            Sort sort = Sort.by("id").descending(); // Sort by unit ID in ascending order

            Page<UnitDtoFavorite> units = unitService.getUnitsByUserCity(userId, PageRequest.of(page, size), sort);

            if (units.hasContent()) {
                return new ResponseEntity<>(units, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("By-Id-General/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        try {
            Unit unit = unitService.getUnitById(id);
            if (unit != null) {
//            List<RoomAvailable> roomAvailableList = new ArrayList<>(unit.getRoomAvailableSet());
//            roomAvailableList.sort(Comparator.comparingLong(RoomAvailable::getId));
//            unit.setRoomAvailableSet(new HashSet<>(roomAvailableList));

                UnitGeneralResponseDto responseDto = unitGeneralResponseMapper.toResponseDto(unit);
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale())));
            }
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("By-Id-For-Residencies/{id}")
    public ResponseEntity<?> getResidenciesUnitById(@PathVariable Long id) {
        try {
            Unit unit = unitService.getUnitById(id);
            if (unit != null) {
                UnitResidenciesResponseDto responseDto = unitResidenciesResponseMapper.toResponseDto(unit);
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale())));
            }
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("Event-Halls-Units/{unitId}")
    public ResponseEntity<?> getEventHallsById(@PathVariable Long unitId) {
        Unit unit = unitService.getUnitById(unitId);
        if (unit == null) {
            ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
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
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"RoomDetails already exists for unitId: " + unitId + " and roomAvailableId: " + roomAvailableId));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,messageSource.getMessage("room_details_already_exists.message", null, LocaleContextHolder.getLocale()) + " " + unitId + "  " + roomAvailableId));
            }


            RoomDetails roomDetails = roomDetailsRequestMapper.toEntity(roomDetailsRequestDto);
            roomDetailsService.addRoomDetails(unitId, roomAvailableId, roomDetails);
//            return ResponseEntity.ok("RoomDetails added successfully " + roomDetails.getId());
            return ResponseEntity.ok(messageSource.getMessage("room_details_added_successfully.message", null, LocaleContextHolder.getLocale()) + " " + roomDetails.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetails: " + e.getMessage());
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("{unitId}/{availableAreaId}/Room-Details-For-Available-Area/Add")
    @Transactional // Add this annotation to enable transaction management
    public ResponseEntity<?> addRoomDetailsForAvailableArea(@PathVariable Long unitId, @PathVariable Long availableAreaId, @RequestBody RoomDetailsRequestDto roomDetailsRequestDto) {
        try {
            // Check if RoomDetailsForAvailableArea already exists for the given unitId and availableAreaId
            boolean roomDetailsExists = roomDetailsForAvailableAreaRepository.existsByUnitIdAndAvailableAreaId(unitId, availableAreaId);
            if (roomDetailsExists) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,"RoomDetailsForAvailableArea already exists for unitId: " + unitId + " and availableAreaId: " + availableAreaId));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400,messageSource.getMessage("room_details_already_exists.message", null, LocaleContextHolder.getLocale()) + " " + unitId + "  " + availableAreaId));
            }

            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsRequestMapper.toEntityAvailableArea(roomDetailsRequestDto);
            roomDetailsForAvailableAreaService.addRoomDetails(unitId, availableAreaId, roomDetailsForAvailableArea);
//            return ResponseEntity.ok("RoomDetailsForAvailableArea added successfully " + roomDetailsForAvailableArea.getId());
            return ResponseEntity.ok(messageSource.getMessage("room_details_added_successfully.message", null, LocaleContextHolder.getLocale()) + " " + roomDetailsForAvailableArea.getId());
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetailsForAvailableArea: " + e.getMessage());
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/Get-By-Unit-And-Room-Available")
    public ResponseEntity<?> getRoomDetailsByUnitAndRoomAvailable(
            @RequestParam Long unitId,
            @RequestParam Long roomAvailableId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);

            if ( roomDetails != null ) {
                // Map RoomDetails entity to RoomDetailsResponseDto
                RoomDetailsResponseDto roomDetailsResponseDto = roomDetailsResponseMapper.toDto(roomDetails);

                // Return the response
                return ResponseEntity.ok(roomDetailsResponseDto);
            } else {
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/Get-By-Unit-And-Available-Area")
    public ResponseEntity<?> getRoomDetailsByUnitAndAvailableArea(
            @RequestParam Long unitId,
            @RequestParam Long availableAreaId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetailsForAvailableArea availableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(unitId, availableAreaId);

            if (availableArea != null) {
                // Map RoomDetails entity to RoomDetailsResponseDto
                RoomDetailsResponseDto roomDetailsResponseDto = roomDetailsResponseMapper.toDtoForAvailableArea(availableArea);
                return ResponseEntity.ok(roomDetailsResponseDto);
            } else {
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/Status-Unit/Unit")
    public ResponseEntity<?> getUnitsForUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Unit> unitPage = unitService.getUnitsForUserAndStatus(userId, statusUnitId, pageable);

            if (unitPage.hasContent()) {
                List<UnitDto> unitDtos = unitMapper.toUnitDtoList(unitPage.getContent());
                return new ResponseEntity<>(unitDtos, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale()));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
//            System.out.println("Error occurred while processing get-units-by-evaluation request: " + e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("Delete/Unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {

        try {
            Unit unit = unitRepository.findById(id).orElse(null);

            StatusUnit statusUnit = statusUnitRepository.findById(4L).orElse(null);

            unit.setStatusUnit(statusUnit);

            unitRepository.save(unit);

//        ApiResponse response = new ApiResponse(200, "Unit_deleted.message");
            ApiResponse response = new ApiResponse(200, messageSource.getMessage("Unit_deleted.message", null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
//            logger.error("Error occurred while processing get-units-by-evaluation request", e);
//            System.out.println("Error occurred while processing get-units-by-evaluation request: " + e);
            System.out.println("Error Message : " + e);
//            ApiResponse response = new ApiResponse(500, "Internal Server Error");
            ApiResponse response = new ApiResponse(500, messageSource.getMessage("internal_server_error.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    private Sort getSortByPrice(String sortDirection) {
        return sortDirection.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
    }

//    private List<UnitDtoFavorite> sortByLocation(List<UnitDtoFavorite> units, Double userLat, Double userLong, String sortDirection) {
//        units.sort(Comparator.comparingDouble(unit -> {
//            double latDiff = userLat - unit.getLatForMapping();
//            double longDiff = userLong - unit.getLongForMapping();
//            return Math.sqrt(latDiff * latDiff + longDiff * longDiff);
//        }));
//
//        if (sortDirection.equalsIgnoreCase("desc")) {
//            Collections.reverse(units);
//        }
//
//        return units;
//    }

    private Sort getSortByEvaluationId(String sortDirection) {
        return sortDirection.equalsIgnoreCase("desc") ? Sort.by("evaluation.id").descending() : Sort.by("evaluation.id").ascending();
    }

    @GetMapping("/Get-Units/{userId}")
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
            @RequestParam(required = false) Double userLng,
            @PathVariable Long userId
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

        User user = userRepository.findById(userId).orElse(null);

        for (Unit unit: unitsPage.getContent()){
            Unit unitForFavorite = unitRepository.findById(unit.getId()).orElse(null);

            if (userFavoriteUnitService.existsByUserAndUnit(user,  unitForFavorite)){
                unitForFavorite.setFavorite(true);
            }
    }
            return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
    }

    @GetMapping("/Filtering/{userId}")
    public ResponseEntity<?> filterUnits(
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long availablePeriodsId,
            @RequestParam(required = false) Long unitTypeId,
            @RequestParam(required = false) Set<Long> accommodationTypeIds,
            @RequestParam(required = false) Set<Long> hotelClassificationIds,
            @RequestParam(required = false) Set<Long> basicFeaturesIds,
            @RequestParam(required = false) Set<Long> subFeaturesIds,
            @RequestParam(required = false) Set<Long> foodOptionsIds,
            @RequestParam(required = false) Set<Long> evaluationId,
            @RequestParam(required = false, defaultValue = "0") int capacityHalls,
            @RequestParam(required = false, defaultValue = "0") int adultsAllowed,
            @RequestParam(required = false, defaultValue = "0") int childrenAllowed,
            @RequestParam(required = false, defaultValue = "0") int priceMin,
            @RequestParam(required = false, defaultValue = "0") int priceMax,
            @PathVariable Long userId) {

        try {
            List<Unit> units = unitService.findUnitsByFilters(cityId, regionId, availablePeriodsId,
                    unitTypeId, accommodationTypeIds, hotelClassificationIds,
                    basicFeaturesIds, subFeaturesIds, foodOptionsIds, evaluationId, capacityHalls, adultsAllowed, childrenAllowed,
                    priceMin, priceMax);

//            List<UnitDtoFavorite>  unitFavoriteDtoList = unitFavoriteMapper.toUnitFavoriteDtoList(units);

            User user = userRepository.findById(userId).orElse(null);

            for (Unit unit: units){
                Unit unitForFavorite = unitRepository.findById(unit.getId()).orElse(null);

                if (userFavoriteUnitService.existsByUserAndUnit(user,  unit)){
                    unitForFavorite.setFavorite(true);
                }
            }

            List<UnitDtoFavorite>  unitFavoriteDtoList = unitFavoriteMapper.toUnitFavoriteDtoList(units);


            return ResponseEntity.ok(unitFavoriteDtoList);

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // You can throw a custom exception or return an error response here if needed
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/Get-Available-Periods")
    public ResponseEntity<?> getAllAvailablePeriods() {
        List<AvailablePeriods> availablePeriods = availablePeriodsService.getAllAvailablePeriods();

        if (!availablePeriods.isEmpty()) {
            return new ResponseEntity<>(availablePeriods, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @PutMapping("Change/Status/Units/{reservationId}/{statusUnitId}")
    public ResponseEntity<?> updateStatusForReservations(@PathVariable Long reservationId, @PathVariable Long statusUnitId) {
        try {

             reservationService.updateStatusForReservation(reservationId, statusUnitId);
//            return ResponseEntity.ok(new ApiResponse(200,"Status changed successfully"));
            return ResponseEntity.ok(new ApiResponse(200,messageSource.getMessage("status_reservation_changed_successful.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {

//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update reservation status : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("failed_updated_status_reservation.message", null, LocaleContextHolder.getLocale()) + " " + e.getMessage());
        }
    }

    @GetMapping("/Get-Reservation-By-Status")
    public ResponseEntity<?> getReservationByStatus(@RequestParam(name = "USER_ID") Long userId,
                                                    @RequestParam(name = "statusId") Long statusId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Unit> unitsPage = unitService.getUnitsByUserId(userId, pageable);

            if (unitsPage == null || unitsPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
            }

            List<ReservationStatus> reservationRequestDtoList = new ArrayList<>();

            for (Unit unit : unitsPage.getContent()) {
                Page<Reservations> reservationsPage = reservationService.getByStatusIdAndUnitId(statusId, unit.getId(), pageable);
                reservationRequestDtoList.addAll(reservationStatusMapper.toReservationStatusDtoList(reservationsPage.getContent()));
            }

            if (reservationRequestDtoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
            }

            return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("Delete/Reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {

        try {
            reservationService.updateStatusForReservation(id, 4L);
            reservationService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200,messageSource.getMessage("reservation_deleted_unit.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
