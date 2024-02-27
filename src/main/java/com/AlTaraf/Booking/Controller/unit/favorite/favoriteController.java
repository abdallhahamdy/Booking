package com.AlTaraf.Booking.Controller.unit.favorite;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.UserFavoriteUnit.UserFavoriteUnitService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class favoriteController {

    @Autowired
    UnitService unitService;

    @Autowired
    private UnitFavoriteMapper unitFavoriteMapper;

    @Autowired
    private UserFavoriteUnitService userFavoriteUnitService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;


    @PostMapping("/user/{userId}/favoriteUnit/{unitId}")
    public ResponseEntity<?> addFavoriteUnit(@PathVariable Long userId, @PathVariable Long unitId) {
        try {
            // Retrieve user and unit from the database
            User user = userRepository.findById(userId).orElse(null);
            Unit unit = unitRepository.findById(unitId).orElse(null);

            if (user == null || unit == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if the user already has the unit in their favorites
            boolean alreadyExists = userFavoriteUnitService.existsByUserAndUnit(user, unit);
            if (alreadyExists) {
                return ResponseEntity.badRequest().body(new ApiResponse(400, "Unit is already in the user's favorite list."));
            }

            // Save the favorite unit for the user
            userFavoriteUnitService.saveUserFavoriteUnit(user, unit);

            return ResponseEntity.ok(new ApiResponse(200, "Added to favorite list successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Failed to add to favorite list."));
        }
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<Page<UnitDtoFavorite>> getUserFavoriteUnitsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UnitDtoFavorite> unitDtoFavoritesPage = userFavoriteUnitService.getUserFavoriteUnitsByUserId(userId, pageable);
        return ResponseEntity.ok().body(unitDtoFavoritesPage);
    }

//    @PatchMapping("/{id}/set-favorite")
//    public ResponseEntity<UnitDtoFavorite> setFavorite(@PathVariable Long id) {
//        Unit unit = unitService.getUnitById(id);
//        UserFavoriteUnit favoriteUnit = new UserFavoriteUnit();
////        favoriteUnit.setUser(this);
//        favoriteUnit.setUnit(unit);
////        userFavoriteUnitService.
//
//
//        if (unit != null) {
//            unit.setFavorite(true); // Set favorite to true
//            unitService.saveUnit(unit);
//
//            // Convert Unit to UnitDtoFavorite using the mapper
//            UnitDtoFavorite unitDtoFavorite = unitFavoriteMapper.toUnitFavoriteDto(unit);
//
//            return new ResponseEntity<>(unitDtoFavorite, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/favorites")
//    public ResponseEntity<Page<UnitDtoFavorite>> getFavoriteUnitsForUser(
//            @RequestParam(name = "userId") Long userId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "4") int size) {
//
//        Page<UnitDtoFavorite> favoriteUnits = unitService.getFavoriteUnitsForUser(userId, page, size);
//
//        if (!favoriteUnits.isEmpty()) {
//            return new ResponseEntity<>(favoriteUnits, HttpStatus.OK);
//        } else {
////            ApiResponse response = new ApiResponse(204, "No Content");
////            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return ResponseEntity.noContent().build();
//        }
//    }

    @DeleteMapping("/{userFavoriteUnitId}")
    public ResponseEntity<?> deleteUserFavoriteUnit(@PathVariable Long userFavoriteUnitId) {
        try {
            userFavoriteUnitService.deleteUserFavoriteUnit(userFavoriteUnitId);
            return ResponseEntity.ok().body(new ApiResponse(200, "UserFavoriteUnit deleted successfully."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(204, "UserFavoriteUnit not found."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "An error occurred while deleting UserFavoriteUnit."));
        }
    }

}
