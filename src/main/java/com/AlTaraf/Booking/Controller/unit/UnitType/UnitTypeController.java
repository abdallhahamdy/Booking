package com.AlTaraf.Booking.Controller.unit.UnitType;

import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.UnitType.UnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-Unit-Type")
    public ResponseEntity<?> getAllUnitType() {
        List<UnitType> unitTypeList = unitTypeService.getAllUnitType();

        if (!unitTypeList.isEmpty()) {
            return new ResponseEntity<>(unitTypeList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
        }
    }
}
