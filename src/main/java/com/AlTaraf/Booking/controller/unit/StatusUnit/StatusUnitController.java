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
@RequestMapping("api/get-All-Status-Unit")
public class StatusUnitController {

    @Autowired
    private StatusUnitService statusUnitService;

    @Autowired
    UnitService unitService;

    @GetMapping
    public ResponseEntity<?> getAllStatusUnit() {
        List<StatusUnit> statusUnitList = statusUnitService.getAllStatusUnit();

        if (!statusUnitList.isEmpty()) {
            return new ResponseEntity<>(statusUnitList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Room Not Available!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
