package com.AlTaraf.Booking.controller.unit.Features;

import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.feature.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class getAllFeatureController {

    @Autowired
    FeatureService featureService;

    @GetMapping("/get-All-Feature")
    public ResponseEntity<?> getAllFeature() {
        List<Feature> featureList = featureService.getAllFeature();

        if (!featureList.isEmpty()) {
            return new ResponseEntity<>(featureList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Features!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
