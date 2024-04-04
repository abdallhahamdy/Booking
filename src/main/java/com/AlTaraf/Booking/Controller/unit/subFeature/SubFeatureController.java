package com.AlTaraf.Booking.Controller.unit.subFeature;

import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.subFeature.SubFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubFeatureController {

    @Autowired
    SubFeatureService subFeatureService;

    @GetMapping("/get-Sub-Feature")
    public ResponseEntity<?> getAllSubFeature() {
        List<SubFeature> subFeatureList = subFeatureService.getAllSubFeature();

        if (!subFeatureList.isEmpty()) {
            return new ResponseEntity<>(subFeatureList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No_content.message");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
