package com.AlTaraf.Booking.controller.roomAvailable;

import com.AlTaraf.Booking.service.unit.RoomAvailable.RoomAvailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/room-Available/room-Details")
public class roomDetailsController {
    @Autowired
    RoomAvailableService roomAvailableService;

    @PostMapping("/insert")
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
