package com.AlTaraf.Booking.controller.unit.roomAvailable;
import com.AlTaraf.Booking.dto.RoomTypeDetailsDTO;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomTypeDetails;
import com.AlTaraf.Booking.mapper.RoomTypeDetailsMapper;
import com.AlTaraf.Booking.repository.unit.RoomDetails.RoomTypeDetailsRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/roomTypeDetails")
public class RoomTypeDetailsController {

    @Autowired
    private RoomTypeDetailsMapper roomTypeDetailsMapper;

    @Autowired
    private RoomTypeDetailsRepository roomTypeDetailsRepository;

    @Autowired
    UnitRepository unitRepository;

    @PostMapping("/{unitId}")
    public ResponseEntity<String> createRoomTypeDetails(@PathVariable Long unitId, @RequestBody RoomTypeDetailsDTO roomTypeDetailsDTO) {
        try {
            // Create RoomTypeDetails entity
            RoomTypeDetails roomTypeDetails = roomTypeDetailsMapper.DTOToRoomTypeDetails(roomTypeDetailsDTO);

            // Fetch the Unit entity using the unitId provided
            Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new EntityNotFoundException("Unit not found"));

            // Set the Unit entity to the RoomTypeDetails entity
            roomTypeDetails.setUnit(unit);

            // Save RoomTypeDetails entity
            roomTypeDetailsRepository.save(roomTypeDetails);

            return ResponseEntity.status(HttpStatus.CREATED).body("Room type details created successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create room type details.");
        }
    }
}
