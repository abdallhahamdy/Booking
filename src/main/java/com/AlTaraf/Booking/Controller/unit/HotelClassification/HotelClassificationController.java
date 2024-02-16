package com.AlTaraf.Booking.Controller.unit.HotelClassification;

import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.hotelClassification.HotelClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelClassificationController {

    @Autowired
    HotelClassificationService hotelClassificationService;

    @GetMapping("/get-All-Hotel-Classifications")
    public ResponseEntity<?> getAllHotelClassifications() {
        List<HotelClassification> classifications = hotelClassificationService.getAllHotelClassification();

        if (!classifications.isEmpty()) {
            return new ResponseEntity<>(classifications, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Hotel Classification Not Available!!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

}
