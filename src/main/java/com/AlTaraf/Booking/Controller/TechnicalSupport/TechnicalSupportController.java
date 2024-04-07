package com.AlTaraf.Booking.Controller.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.Mapper.TechnicalSupport.TechnicalSupportUnitsMapper;
import com.AlTaraf.Booking.Payload.request.TechnicalSupport.TechnicalSupportRequest;
import com.AlTaraf.Booking.Payload.request.TechnicalSupport.TechnicalSupportUnitsRequest;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.Service.TechnicalSupport.TechnicalSupportUnitsService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/technical-support")
public class TechnicalSupportController {

    @Autowired
    TechnicalSupportService technicalSupportService;

    @Autowired
    TechnicalSupportUnitsService technicalSupportUnitsService;

    @Autowired
    UserService userService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitTechnicalSupport( @RequestBody TechnicalSupportRequest technicalSupportRequest) {
        try {
            // You might want to perform additional validation or business logic here
            TechnicalSupport technicalSupport = TechnicalSupportMapper.INSTANCE.toEntity(technicalSupportRequest);
            technicalSupportService.saveTechnicalSupport(technicalSupport);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Technical_Support.message"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Technical_Support_Error.message "));
        }
    }

    @PostMapping("Technical-Support-Units/submit")
    public ResponseEntity<?> submitTechnicalSupportUnits( @RequestBody TechnicalSupportUnitsRequest technicalSupportUnitsRequest) {
        try {
            // You might want to perform additional validation or business logic here
            TechnicalSupportForUnits technicalSupportForUnits = TechnicalSupportUnitsMapper.INSTANCE.toEntity(technicalSupportUnitsRequest);
            technicalSupportUnitsService.saveTechnicalSupport(technicalSupportForUnits);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Technical_Support.message"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(400, "Technical_Support_Error.message"));
        }
    }

}
