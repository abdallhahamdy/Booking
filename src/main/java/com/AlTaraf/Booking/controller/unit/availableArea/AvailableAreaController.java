package com.AlTaraf.Booking.controller.unit.availableArea;

import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.availableArea.AvailableAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AvailableAreaController {
    @Autowired
    AvailableAreaService availableAreaService;

    @GetMapping("/get-Available-Area")
    public ResponseEntity<?> getAllAvailableArea() {
        List<AvailableArea> availableAreaList = availableAreaService.getAllAvailableArea();

        if (!availableAreaList.isEmpty()) {
            return new ResponseEntity<>(availableAreaList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Food Options!"));
        }
    }
}
