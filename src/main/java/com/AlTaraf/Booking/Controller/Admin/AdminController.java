package com.AlTaraf.Booking.Controller.Admin;


import com.AlTaraf.Booking.Dto.Roles.RoleDashboardDto;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportUnitsMapper;
import com.AlTaraf.Booking.Payload.response.TechnicalSupport.TechnicalSupportResponse;
import com.AlTaraf.Booking.Dto.Unit.UnitDashboard;
import com.AlTaraf.Booking.Dto.User.UserDashboard;
import com.AlTaraf.Booking.Dto.User.UserRegisterDashboardDto;
import com.AlTaraf.Booking.Dto.packageAds.PackageAdsEditDTO;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.RoleDashboardMapper;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.Mapper.Unit.*;
import com.AlTaraf.Booking.Mapper.Unit.Dashboard.UnitDashboardMapper;
import com.AlTaraf.Booking.Mapper.Unit.Dashboard.UserDashboardMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.TechnicalSupport.TechnicalSupportUnitsResponse;
import com.AlTaraf.Booking.Payload.response.Unit.UnitGeneralResponseDto;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.Ads.PackageAdsRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportRepository;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportUnitRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportUnitsService;
import com.AlTaraf.Booking.Service.role.RoleDashboardService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin
public class AdminController {

    @Autowired
    TechnicalSupportService technicalSupportService;

    @Autowired
    TechnicalSupportUnitsService technicalSupportUnitsService;

    @Autowired
    UserService userService;

    @Autowired
    UnitService unitService;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    EventHallsMapper eventHallsMapper;

    @Autowired
    UnitResidenciesResponseMapper unitResidenciesResponseMapper;

    @Autowired
    private UnitGeneralResponseMapper unitGeneralResponseMapper;

    @Autowired
    private AdsService adsService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoleDashboardService roleDashboardService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private ReserveDateRepository reserveDateRepository;

    @Autowired
    private ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    private UnitDashboardMapper unitDashboard;

    @Autowired
    private UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Autowired
    private RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDashboardMapper userDashboardMapper;

    @Autowired
    private PackageAdsRepository packageAdsRepository;

    @Autowired
    private TechnicalSupportRepository technicalSupportRepository;

    @Autowired
    private TechnicalSupportUnitRepository technicalSupportUnitRepository;

    @Autowired
    private TechnicalSupportMapper technicalSupportMapper;

    @Autowired
    private TechnicalSupportUnitsMapper technicalSupportUnitsMapper;

    @CrossOrigin
    @PostMapping("/Register-Dashboard")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDashboardDto userRegisterDashboardDto) {

        // Perform user registration
        userService.registerUserForDashboard(userRegisterDashboardDto);

        ApiResponse response = new ApiResponse(200, "User Registered Successfully!");

        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/Role-All")
    public ResponseEntity<?> getAllRoles() {
        List<RoleDashboard> roleDashboards = roleDashboardService.getAllRoles();
        if (!roleDashboards.isEmpty()) {
            List<RoleDashboardDto> roleDashboardDtosDtos = RoleDashboardMapper.INSTANCE.rolesToRoleDtos(roleDashboards);
            return ResponseEntity.ok(roleDashboardDtosDtos);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Roles!");

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @CrossOrigin
    @GetMapping("/Technical-Support-Get-All")
    public Page<TechnicalSupportResponse> getAllTechnicalSupport(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        Page<TechnicalSupport> technicalSupportPage = technicalSupportService.getAllTechnicalSupport(PageRequest.of(page, size));
        List<TechnicalSupportResponse> technicalSupportResponseList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportResponseList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
    }

    @CrossOrigin
    @GetMapping("/Technical-Support-Unit-Get-All")
    public Page<TechnicalSupportUnitsResponse> getAllTechnicalSupportUnit(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        Page<TechnicalSupportForUnits> technicalSupportPage = technicalSupportUnitsService.getAllTechnicalSupport(PageRequest.of(page, size));
        List<TechnicalSupportUnitsResponse> technicalSupportResponseList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportUnitsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportResponseList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
    }

    @CrossOrigin
    @PatchMapping("Technical-Support/{id}/mark-as-seen")
    public ResponseEntity<?> markAsSeen(@PathVariable Long id) {
        Optional<TechnicalSupport> optionalTechnicalSupport = technicalSupportRepository.findById(id);
        if (optionalTechnicalSupport.isPresent()) {
            TechnicalSupport technicalSupport = optionalTechnicalSupport.get();
            technicalSupport.setSeen(true);
            technicalSupportRepository.save(technicalSupport);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Marked As seen"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Technical Support Id Not Found"));
        }
    }

    @CrossOrigin
    @PatchMapping("Technical-Support-Unit/{id}/mark-as-seen")
    public ResponseEntity<?> markAsSeenUnit(@PathVariable Long id) {
        Optional<TechnicalSupportForUnits> optionalTechnicalSupport = technicalSupportUnitRepository.findById(id);
        if (optionalTechnicalSupport.isPresent()) {
            TechnicalSupportForUnits technicalSupportForUnits = optionalTechnicalSupport.get();
            technicalSupportForUnits.setSeen(true);
            technicalSupportUnitRepository.save(technicalSupportForUnits);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Marked As seen"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Technical Support Unit Id Not Found"));
        }
    }

    @CrossOrigin
    @GetMapping("/Technical-Support/{id}")
    public ResponseEntity<TechnicalSupportResponse> getByIdTechnicalSupport(@PathVariable Long id) {
        Optional<TechnicalSupport> optionalTechnicalSupport = technicalSupportRepository.findById(id);
        return optionalTechnicalSupport
                .map(technicalSupport -> ResponseEntity.ok().body(technicalSupportMapper.toDto(technicalSupport)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("/Technical-Support-Units/{id}")
    public ResponseEntity<TechnicalSupportUnitsResponse> getByIdTechnicalSupportUnits(@PathVariable Long id) {
        Optional<TechnicalSupportForUnits> optionalTechnicalSupport = technicalSupportUnitRepository.findById(id);
        return optionalTechnicalSupport
                .map(technicalSupportForUnits -> ResponseEntity.ok().body(technicalSupportUnitsMapper.toDto(technicalSupportForUnits)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Get-Units-By-Accommodation-Type")
    @CrossOrigin
    public ResponseEntity<?> getUnitsByAccommodationType(
            @RequestParam String accommodationTypeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Page<UnitDashboard> units = unitService.getUnitsByAccommodationTypeNameDashboard(accommodationTypeName, page, size);

        if (!units.isEmpty()) {
//            Page<UnitDashboard> unitDashboardPage = units.map(unit -> unitDashboard.toUnitDashboard(unit));
            return ResponseEntity.ok(units);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Units By Accommodation Type!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

//    return unitsPage.map(unit -> unitDashboard.toUnitDashboard(unit));

//    @GetMapping("/Get-Units-By-Accommodation-Type")
//    public ResponseEntity<?> getUnitsByAccommodationType(
//            @RequestParam String accommodationTypeName,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size) {
//
//        Page<UnitDtoFavorite> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeName, page, size);
//
//        if (!units.isEmpty()) {
//            return new ResponseEntity<>(units, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content for Units By Accommodation Type!");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//        }
//    }

    @DeleteMapping("/delete/{id}/Technical-Support")
    @CrossOrigin
    public ResponseEntity<?> deleteTechnicalSupportById(@PathVariable Long id) {
        try {
            technicalSupportService.deleteTechnicalSupportById(id);
            return ResponseEntity.ok(new ApiResponse(200, "Technical support message deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete technical support message."));
        }
    }

    @DeleteMapping("/deleteAll/Technical-Support")
    @CrossOrigin
    public ResponseEntity<?> deleteAllTechnicalSupport() {
        try {
            technicalSupportService.deleteAllTechnicalSupport();
            return ResponseEntity.ok(new ApiResponse(200, "All technical support messages deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete all technical support messages."));
        }
    }

    @DeleteMapping("/delete/{id}/Technical-Support-Units")
    @CrossOrigin
    public ResponseEntity<?> deleteTechnicalSupportUnitsById(@PathVariable Long id) {
        try {
            technicalSupportUnitsService.deleteTechnicalSupportById(id);
            return ResponseEntity.ok(new ApiResponse(200, "Technical support Unit message deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete technical support Unit message."));
        }
    }

    @DeleteMapping("/deleteAll/Technical-Support-Units")
    @CrossOrigin
    public ResponseEntity<?> deleteAllTechnicalSupportUnits() {
        try {
            technicalSupportUnitsService.deleteAllTechnicalSupport();
            return ResponseEntity.ok(new ApiResponse(200, "All technical support Unit messages deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete all technical support Unit messages."));
        }
    }

//    @GetMapping("/Get-Units")
//    public Page<UnitDtoFavorite> getUnits(
//            @RequestParam(required = false) String nameUnit,
//            @RequestParam(required = false) Long unitTypeId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//        Page<Unit> unitsPage = Page.empty();
//
//        if (nameUnit == null && unitTypeId != null) {
//            unitsPage = unitService.getUnitsByUnitTypeId(unitTypeId, PageRequest.of(page, size));
//        }
//        else if (nameUnit != null && unitTypeId == null) {
//            unitsPage = unitService.filterUnitsByName(nameUnit, PageRequest.of(page, size));
//        }
//        else if (nameUnit != null && unitTypeId != null) {
//            unitsPage = unitService.filterUnitsByNameAndTypeId(nameUnit, unitTypeId, PageRequest.of(page, size));
//        }
//        else if (nameUnit == null && unitTypeId == null) {
//            unitsPage = unitService.getAllUnit(PageRequest.of(page, size));
//        }
//        return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
//    }

    @GetMapping("/Get-Units-For-Dashboard")
    @CrossOrigin
    public Page<UnitDashboard> getUnitsForDashboard(
            @RequestParam(required = false) String traderName,
            @RequestParam(required = false) String traderPhone,
            @RequestParam Long unitTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Unit> unitsPage = Page.empty();

        if (traderName == null && traderPhone == null && unitTypeId != null) {
            unitsPage = unitService.getUnitsByUnitTypeId(unitTypeId, PageRequest.of(page, size));
        }
//        else if (traderName != null && unitTypeId == null) {
//            unitsPage = unitService.filterUnitsByName(traderName, PageRequest.of(page, size));
//        }
        else if (traderName != null && unitTypeId != null) {
            unitsPage = unitService.filterUnitsByUserNameAndTypeId(traderName, unitTypeId, PageRequest.of(page, size));
        }
        else if (traderName == null && unitTypeId != null && traderPhone != null) {
            unitsPage = unitService.filterUnitsByPhoneNumberAndTypeId(traderPhone, unitTypeId,PageRequest.of(page, size));
        }
        return unitsPage.map(unit -> unitDashboard.toUnitDashboard(unit));
    }

    @GetMapping("By-Id-General/{id}")
    @CrossOrigin
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        if (unit != null) {
            UnitGeneralResponseDto responseDto = unitGeneralResponseMapper.toResponseDto(unit);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Not Found!"));
        }
    }


//    @GetMapping("/Get-Units")
//    public Page<UnitDtoFavorite> getUnits(
//            @RequestParam(required = false) String nameUnit,
//            @RequestParam(required = false) String roomAvailableName,
//            @RequestParam(required = false) String availableAreaName,
//            @RequestParam(required = false) Long unitTypeId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "asc") String sortDirection) {
//        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
//        Page<Unit> unitsPage = Page.empty();
//        if (nameUnit == null && unitTypeId == null && roomAvailableName == null && availableAreaName == null) {
//            unitsPage = unitService.getAllUnit(PageRequest.of(page, size, sort));
//        }
//        return unitsPage.map(unit -> unitFavoriteMapper.toUnitFavoriteDto(unit));
//    }

    @PutMapping("Change/Status/Ads/{adsId}/{statusUnitId}")
    @CrossOrigin
    public ResponseEntity<?> updateStatusForAds(@PathVariable Long adsId, @PathVariable Long statusUnitId) {
        try {
            adsService.updateStatusForAds(adsId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @PutMapping("Change/Status/Units/{unitId}/{statusUnitId}")
    @CrossOrigin
    public ResponseEntity<?> updateStatusForUnits(@PathVariable Long unitId, @PathVariable Long statusUnitId) {
        try {
            unitService.updateStatusForUser(unitId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @CrossOrigin
    @Transactional
    @DeleteMapping("Delete/Unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {

        try {

            userFavoriteUnitRepository.deleteByUnit(id);
            reserveDateRepository.deleteByUnitId(id);
            reserveDateHallsRepository.deleteRelatedDateInfoHallsByUnitId(id);
            roomDetailsForAvailableAreaRepository.deleteByUnitId(id);

            reserveDateHallsRepository.deleteByUnitId(id);
            roomDetailsRepository.deleteByUnitId(id);

            imageDataRepository.deleteByUnitId(id);
            adsRepository.deleteByUnitId(id);
            reservationRepository.deleteByUnitId(id);

            unitService.deleteUnit(id);
            ApiResponse response = new ApiResponse(200, "Unit deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("Error Message Delete Unit: " + e);
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @CrossOrigin
    @PutMapping("/{userId}/ban")
    public ResponseEntity<?> toggleBanStatus(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        if (user.getBan() == null) {
            user.setBan(true);
        } else {
            user.setBan(!user.getBan());
        }
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Ban for User are Changed"));
    }

    @CrossOrigin
    @GetMapping("/Get-User-All-Or-ByRole")
    public ResponseEntity<Page<?>> getUsersByRole(
            @RequestParam(required = false) ERole roleName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> usersPage;
        if (roleName != null && username != null && phone != null) {
            usersPage = userRepository.findAllByRolesNameAndUsernameAndPhone(roleName, username, phone, pageable);
        } else if (roleName != null && username == null && phone == null) {
            usersPage = userRepository.findAllByRolesName(roleName, pageable);
        } else if (roleName != null && username != null && phone == null) {
            usersPage = userRepository.findByUsernameAndRolesName(username, roleName, pageable);
        } else if (roleName == null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhone(phone, pageable);
        }
        else if (roleName == null && phone == null && username != null ) {
            usersPage = userRepository.findByUsername(username, pageable);
        } else if (roleName != null && username == null && phone != null) {
            usersPage = userRepository.findAllByPhoneAndRolesName(phone, roleName, pageable);
        }
        else {
            usersPage = userRepository.findAll(pageable);
        }
        Page<UserDashboard> userDashboardPage = usersPage.map(userDashboardMapper::toUserDashboard);

        return ResponseEntity.ok(userDashboardPage);
    }

    @CrossOrigin
    @PatchMapping("Edit-Package-Ads/{id}")
    public ResponseEntity<?> editPackageAds(@PathVariable Long id, @RequestBody PackageAdsEditDTO packageAdsEditDTO) {
        Optional<PackageAds> optionalPackageAds = packageAdsRepository.findById(id);
        if (optionalPackageAds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PackageAds packageAds = optionalPackageAds.get();
        if (packageAdsEditDTO.getArabicName() != null) {
            packageAds.setArabicName(packageAdsEditDTO.getArabicName());
        }
        if (packageAdsEditDTO.getPrice() != 0) {
            packageAds.setPrice(packageAdsEditDTO.getPrice());
        }
        if (packageAdsEditDTO.getNumberAds() != 0) {
            packageAds.setNumberAds(packageAdsEditDTO.getNumberAds());
        }

        packageAdsRepository.save(packageAds);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Updated Package Ads successfully"));
    }

    @CrossOrigin
    @DeleteMapping("/Delete-Users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserAndAssociatedEntities(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PutMapping("/{userId}/warnings")
    public ResponseEntity<?> setWarnings(@PathVariable Long userId, @RequestBody List<Boolean> warnings) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            user.setWarnings(warnings);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Warning Added Successfully"));
        } catch (Exception e) {
            e.printStackTrace(); // You can log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Warning Not Added");
        }
    }
}
