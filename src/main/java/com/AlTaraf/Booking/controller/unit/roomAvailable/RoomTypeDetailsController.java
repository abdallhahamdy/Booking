package com.AlTaraf.Booking.controller.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.mapper.Unit.RoomDetails.RoomDetailsRequestMapper;
import com.AlTaraf.Booking.mapper.Unit.RoomDetails.RoomDetailsResponseMapper;
import com.AlTaraf.Booking.payload.request.RoomDetails.RoomDetailsRequestDto;
import com.AlTaraf.Booking.payload.response.RoomDetails.RoomDetailsResponseDto;
import com.AlTaraf.Booking.repository.unit.RoomDetails.RoomTypeDetailsRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import com.AlTaraf.Booking.service.unit.RoomDetailsService.RoomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/roomTypeDetails")
public class RoomTypeDetailsController {

//    @Autowired
//    private RoomTypeDetailsMapper roomTypeDetailsMapper;

    @Autowired
    private RoomTypeDetailsRepository roomTypeDetailsRepository;

    @Autowired
    private RoomDetailsResponseMapper roomDetailsResponseMapper;


    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private RoomDetailsRequestMapper roomDetailsRequestMapper;

    @Autowired
    UnitRepository unitRepository;

//    @PostMapping("/{unitId}")
//    public ResponseEntity<String> createRoomTypeDetails(@PathVariable Long unitId, @RequestBody RoomTypeDetailsDTO roomTypeDetailsDTO) {
//        try {
//            // Create RoomTypeDetails entity
//            RoomTypeDetails roomTypeDetails = roomTypeDetailsMapper.DTOToRoomTypeDetails(roomTypeDetailsDTO);
//
//            // Fetch the Unit entity using the unitId provided
//            Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new EntityNotFoundException("Unit not found"));
//
//            // Set the Unit entity to the RoomTypeDetails entity
//            roomTypeDetails.setUnit(unit);
//
//            // Save RoomTypeDetails entity
//            roomTypeDetailsRepository.save(roomTypeDetails);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("Room type details created successfully.");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create room type details.");
//        }
//    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addRoomDetails(@RequestParam Long unitId, @RequestParam Long roomAvailableId, @RequestBody RoomDetailsRequestDto roomDetailsDto) {
//        try {
//            RoomDetails roomDetails = roomDetailsRequestMapper.toEntity(roomDetailsDto);
//
//            roomDetailsService.addRoomDetails(unitId, roomAvailableId, roomDetails);
//            return ResponseEntity.ok("RoomDetails added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add RoomDetails: " + e.getMessage());
//        }
//    }

    @GetMapping("/getByUnitAndRoomAvailable")
    public ResponseEntity<RoomDetailsResponseDto> getRoomDetailsByUnitAndRoomAvailable(
            @RequestParam Long unitId,
            @RequestParam Long roomAvailableId) {
        try {
            // Retrieve RoomDetails entity from the service layer
            RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);

            // Map RoomDetails entity to RoomDetailsResponseDto
            RoomDetailsResponseDto roomDetailsResponseDto = roomDetailsResponseMapper.toDto(roomDetails);

            // Return the response
            return ResponseEntity.ok(roomDetailsResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
