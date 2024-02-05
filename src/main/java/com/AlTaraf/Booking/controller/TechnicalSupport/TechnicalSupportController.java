package com.AlTaraf.Booking.controller.TechnicalSupport;

import com.AlTaraf.Booking.dto.TechnicalSupport.TechnicalSupportDTO;
import com.AlTaraf.Booking.entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.mapper.TechnicalSupport.TechnicalSupportMapper;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.TechnicalSupport.TechnicalSupportService;
import com.AlTaraf.Booking.service.user.UserService;
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
    UserService userService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitTechnicalSupport( @RequestBody TechnicalSupportDTO technicalSupportDTO) {
        try {
            // You might want to perform additional validation or business logic here
            TechnicalSupport technicalSupport = TechnicalSupportMapper.INSTANCE.toEntity(technicalSupportDTO);
            technicalSupportService.saveTechnicalSupport(technicalSupport);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, "The Message Sent to technical Support Team."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "The Message doesn't Sent "));
        }
    }
}
