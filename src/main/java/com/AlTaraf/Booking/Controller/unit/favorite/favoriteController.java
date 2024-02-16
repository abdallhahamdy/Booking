package com.AlTaraf.Booking.Controller.unit.favorite;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Unit.UnitFavoriteMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PatchMapping("/{id}/set-favorite")
    public ResponseEntity<UnitDtoFavorite> setFavorite(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);

        if (unit != null) {
            unit.setFavorite(true); // Set favorite to true
            unitService.saveUnit(unit);

            // Convert Unit to UnitDtoFavorite using the mapper
            UnitDtoFavorite unitDtoFavorite = unitFavoriteMapper.toUnitFavoriteDto(unit);

            return new ResponseEntity<>(unitDtoFavorite, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<Page<UnitDtoFavorite>> getFavoriteUnitsForUser(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Page<UnitDtoFavorite> favoriteUnits = unitService.getFavoriteUnitsForUser(userId, page, size);

        if (!favoriteUnits.isEmpty()) {
            return new ResponseEntity<>(favoriteUnits, HttpStatus.OK);
        } else {
//            ApiResponse response = new ApiResponse(204, "No Content");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/{id}/delete-favorite")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);

        if (unit != null) {
            unit.setFavorite(false); // Set favorite to true
            unitService.saveUnit(unit);
            ApiResponse response = new ApiResponse(200, "Unit deleted from favorite successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
//            return new ResponseEntity<>(unit, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
