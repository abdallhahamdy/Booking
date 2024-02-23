package com.AlTaraf.Booking.Payload.request.Reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private String clientName;
    private String clientPhone;
    private Long unitId;
    private Long userId;
    private Long roomAvailableId;
    private Long availableAreaId;
//    private Set<Long> roomAvailableIds;
//    private Set<Long> availableAreaIds;
    private Set<Long> basicFeaturesIds;
    private Set<Long> subFeaturesIds;
    private Set<Long> foodOptionsIds;
    private int capacityHalls;
    private Set<Long> availablePeriodsHallsIds;
    private int adultsAllowed;
    private int childrenAllowed;
    private Long evaluationId;

//    @GetMapping("/Get-By-Unit-And-Room-Available")
//    public ResponseEntity<?> getRoomDetailsByUnitAndRoomAvailable(
//            @RequestParam Long unitId,
//            @RequestParam Long roomAvailableId) {
//        try {
//            // Retrieve RoomDetails entity from the service layer
//            RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);
//
//            // Map RoomDetails entity to RoomDetailsResponseDto
//            RoomDetailsRequestDto roomDetailsResponseDto = roomDetailsRequestMapper.toDto(roomDetails);
//
//            // Return the response
//            return ResponseEntity.ok(roomDetailsResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/Get-By-Unit-And-Available-Area")
//    public ResponseEntity<?> getRoomDetailsByUnitAndAvailableArea(
//            @RequestParam Long unitId,
//            @RequestParam Long availableAreaId) {
//        try {
//            // Retrieve RoomDetails entity from the service layer
//            RoomDetailsForAvailableArea availableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(unitId, availableAreaId);
//
//            // Map RoomDetails entity to RoomDetailsResponseDto
//            RoomDetailsRequestDto roomDetailsResponseDto = roomDetailsRequestMapper.toDtoForAvailableArea(availableArea);
//
//            // Return the response
//            return ResponseEntity.ok(roomDetailsResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}
