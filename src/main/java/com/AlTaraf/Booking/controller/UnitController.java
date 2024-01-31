package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    UnitService unitService;
    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        Unit savedUnit = unitService.saveUnit(unit);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }
}
