package com.AlTaraf.Booking.Controller.Reservations;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationGetByIdMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationRequestMapper;
import com.AlTaraf.Booking.Payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.Payload.request.UnitRequestDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRequestMapper reservationRequestMapper;

    @Autowired
    ReservationGetByIdMapper reservationGetByIdMapper;

    @PostMapping("/Create-Reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);

            // Save the unit in the database
            Reservations saveReservation = reservationService.saveReservation(reservationsToSave);

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
}
