package com.AlTaraf.Booking.controller.unit.AccommodationType;

import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.AccommodationType.AccommodationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccommodationTypeController {

    @Autowired
    AccommodationTypeService accommodationTypeService;

    @GetMapping("/get-Accommodation-Type")
    public ResponseEntity<?> getAccommodationType() {
        List<AccommodationType> accommodationTypeList = accommodationTypeService.getAllAccommodationType();

        if (!accommodationTypeList.isEmpty()) {
            return new ResponseEntity<>(accommodationTypeList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Accommodation Type!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
