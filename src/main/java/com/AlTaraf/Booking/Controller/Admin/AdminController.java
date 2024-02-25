package com.AlTaraf.Booking.Controller.Admin;


import com.AlTaraf.Booking.Dto.TechnicalSupport.TechnicalSupportDTO;
import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.Mapper.Unit.EventHallsMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitGeneralResponseMapper;
import com.AlTaraf.Booking.Mapper.Unit.UnitResidenciesResponseMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Unit.UnitGeneralResponseDto;
import com.AlTaraf.Booking.Payload.response.Unit.UnitResidenciesResponseDto;
import com.AlTaraf.Booking.Service.Ads.AdsService;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Api/Admin")
public class AdminController {

    @Autowired
    TechnicalSupportService technicalSupportService;

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

    @GetMapping("/Technical-Support-Get-All")
    public Page<TechnicalSupportDTO> getAllTechnicalSupport(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size) {
        Page<TechnicalSupport> technicalSupportPage = technicalSupportService.getAllTechnicalSupport(PageRequest.of(page, size));
        List<TechnicalSupportDTO> technicalSupportDTOList = technicalSupportPage.getContent().stream()
                .map(TechnicalSupportMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(technicalSupportDTOList, PageRequest.of(page, size), technicalSupportPage.getTotalElements());
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

    @DeleteMapping("/delete/{id}/Technical-Support")
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
    public ResponseEntity<?> deleteAllTechnicalSupport() {
        try {
            technicalSupportService.deleteAllTechnicalSupport();
            return ResponseEntity.ok(new ApiResponse(200, "All technical support messages deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, "Failed to delete all technical support messages."));
        }
    }

    @GetMapping("/Get-Units")
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
    public ResponseEntity<?> updateStatusForAds(@PathVariable Long adsId, @PathVariable Long statusUnitId) {
        try {
            adsService.updateStatusForAds(adsId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }

    @PutMapping("Change/Status/Units/{unitId}/{statusUnitId}")
    public ResponseEntity<?> updateStatusForUnits(@PathVariable Long unitId, @PathVariable Long statusUnitId) {
        try {
            unitService.updateStatusForUser(unitId, statusUnitId);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }


}