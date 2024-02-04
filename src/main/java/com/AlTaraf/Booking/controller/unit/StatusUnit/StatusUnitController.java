package com.AlTaraf.Booking.controller.unit.StatusUnit;

import com.AlTaraf.Booking.dto.Unit.UnitDto;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.mapper.Unit.UnitMapper;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.UnitService;
import com.AlTaraf.Booking.service.unit.statusUnit.StatusUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatusUnitController {

    @Autowired
    private StatusUnitService statusUnitService;

    @Autowired
    UnitService unitService;

    @Autowired
    UnitMapper unitMapper;

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
}
