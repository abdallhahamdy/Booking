package com.AlTaraf.Booking.controller.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.unit.RoomAvailable.RoomAvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/room-Available")
public class roomAvailableController {
    @Autowired
    RoomAvailableService roomAvailableService;

    @GetMapping("/get-All")
    public ResponseEntity<?> getAllRoomAvailable() {
        List<RoomAvailable> roomAvailables = roomAvailableService.getAllRoomAvailable();

        if (!roomAvailables.isEmpty()) {
            return new ResponseEntity<>(roomAvailables, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "No Content for Room Not Available!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/insert-room-Details")
    public ResponseEntity<String> insertRoomDetails(
            @RequestParam Long roomAvailableId,
            @RequestParam int roomNumber,
            @RequestParam BigDecimal newPrice,
            @RequestParam BigDecimal oldPrice) {

        try {
            roomAvailableService.insertRoomDetails(roomAvailableId, roomNumber, newPrice, oldPrice);
            return ResponseEntity.ok("RoomDetails inserted successfully!");
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting RoomDetails");
        }
    }
}
