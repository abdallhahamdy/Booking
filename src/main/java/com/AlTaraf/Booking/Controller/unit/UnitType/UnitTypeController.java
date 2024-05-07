package com.AlTaraf.Booking.Controller.unit.UnitType;

import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.UnitType.UnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UnitTypeController {
    @Autowired
    UnitTypeService unitTypeService;

    @GetMapping("/get-Unit-Type")
    public ResponseEntity<?> getAllUnitType() {
        List<UnitType> unitTypeList = unitTypeService.getAllUnitType();

        if (!unitTypeList.isEmpty()) {
            return new ResponseEntity<>(unitTypeList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "no_content.message");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
