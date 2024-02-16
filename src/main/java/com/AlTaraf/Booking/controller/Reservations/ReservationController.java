package com.AlTaraf.Booking.controller.Reservations;

import com.AlTaraf.Booking.entity.Reservations;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.mapper.Reservation.ReservationRequestMapper;
import com.AlTaraf.Booking.payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.payload.request.UnitRequestDto;
import com.AlTaraf.Booking.payload.response.ApiResponse;
import com.AlTaraf.Booking.service.Reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRequestMapper reservationRequestMapper;

    @PostMapping("/Create-Reservation")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);

            // Save the unit in the database
            Reservations saveReservation = reservationService.saveReservation(reservationsToSave);

            // Return the unitId in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation Process is successfully " );
        } catch (Exception e) {
//            // Log the exception
//            logger.error("Error occurred while processing create-unit request", e);

            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed to create Reservation. Please check your input and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
