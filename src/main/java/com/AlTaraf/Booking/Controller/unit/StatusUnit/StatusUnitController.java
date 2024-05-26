package com.AlTaraf.Booking.Controller.unit.StatusUnit;

import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.unit.statusUnit.StatusUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private MessageSource messageSource;

    @Autowired
    UnitService unitService;

    @GetMapping
    public ResponseEntity<?> getAllStatusUnit() {
        List<StatusUnit> statusUnitList = statusUnitService.getAllStatusUnit();

        if (!statusUnitList.isEmpty()) {
            return new ResponseEntity<>(statusUnitList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
