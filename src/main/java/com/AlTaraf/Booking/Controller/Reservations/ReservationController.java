package com.AlTaraf.Booking.Controller.Reservations;

import com.AlTaraf.Booking.Dto.Unit.UnitDto;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationGetByIdMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationRequestMapper;
import com.AlTaraf.Booking.Payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.unit.RoomDetails.RoomDetailsService;
import com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea.RoomDetailsForAvailableAreaService;
import com.AlTaraf.Booking.Service.unit.availableArea.AvailableAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRequestMapper reservationRequestMapper;

    @Autowired
    ReservationGetByIdMapper reservationGetByIdMapper;

    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private RoomDetailsForAvailableAreaService roomDetailsForAvailableAreaService;

    @Autowired
    private AvailableAreaService availableAreaService;

    @PostMapping("/Create-Reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);

            if (reservationRequestDto.getRoomAvailableId() != null) {
                RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(reservationRequestDto.getUnitId(), reservationRequestDto.getRoomAvailableId());
                if (roomDetails != null) {
                    // Update the price based on room details
                    if (roomDetails.getNewPrice() < roomDetails.getOldPrice()) {
                        reservationsToSave.setPrice(roomDetails.getNewPrice());
                        System.out.println("getRoomAvailable roomDetails.getNewPrice(): " + roomDetails.getNewPrice());
                    } else {
                        reservationsToSave.setPrice(roomDetails.getOldPrice());
                        System.out.println("roomDetailsForAvailableArea.getOldPrice()): " + roomDetails.getOldPrice());
                    }
                }
            }


            else if (reservationRequestDto.getAvailableAreaId() != null) {
                RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(reservationsToSave.getUnit().getId(), reservationsToSave.getAvailableArea().getId());
                if (roomDetailsForAvailableArea != null) {
                    // Update the price based on available area details
                    if (roomDetailsForAvailableArea.getNewPrice() < roomDetailsForAvailableArea.getOldPrice()) {
                        reservationsToSave.setPrice(roomDetailsForAvailableArea.getNewPrice());
                        System.out.println("getAvailableArea roomDetailsForAvailableArea.getNewPrice(): " + roomDetailsForAvailableArea.getNewPrice());
                    } else {
                        reservationsToSave.setPrice(roomDetailsForAvailableArea.getOldPrice());
                        System.out.println("roomDetailsForAvailableArea.getOldPrice()): " + roomDetailsForAvailableArea.getOldPrice());
                    }
                }
            }
                // Save the unit in the database
                Reservations saveReservation = reservationService.saveReservation(reservationsToSave);

                // Check if the reservation has room available or available area

            // Return the unitId in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation Process is successfully with id: " + saveReservation.getId() );
        } catch (Exception e) {
//            // Log the exception
//            logger.error("Error occurred while processing create-unit request", e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to create Reservation. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseGetId> getReservationById(@PathVariable Long id) {
        Reservations reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        ReservationResponseGetId response = reservationGetByIdMapper.toResponseDto(reservation);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/Status-Unit")
//    public ResponseEntity<?> getReservationsForUserAndStatus(
//            @RequestParam(name = "USER_ID") Long userId,
//            @RequestParam(name = "statusUnitName") String statusUnitName) {
//
//        List<Reservations> reservations = reservationService.getReservationsForUserAndStatus(userId, statusUnitName);
//
//        if (!reservations.isEmpty()) {
//            List<ReservationResponseGetId> reservationRequestDtoList = ReservationRequestMapper.(reservations);
//            return new ResponseEntity<>(unitDtos, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//        }
//    }
}
