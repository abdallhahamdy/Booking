package com.AlTaraf.Booking.Controller.Reservations;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationGetByIdMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationRequestMapper;
import com.AlTaraf.Booking.Mapper.Reservation.ReservationStatusMapper;
import com.AlTaraf.Booking.Payload.request.Reservation.ReservationRequestDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationStatus;
import com.AlTaraf.Booking.Repository.Evaluation.EvaluationRepository;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Service.Reservation.ReservationService;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Service.unit.RoomDetails.RoomDetailsService;
import com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea.RoomDetailsForAvailableAreaService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import com.AlTaraf.Booking.Service.unit.availableArea.AvailableAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
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
    UnitService unitService;

    @Autowired
    private RoomDetailsService roomDetailsService;

    @Autowired
    private RoomDetailsForAvailableAreaService roomDetailsForAvailableAreaService;

    @Autowired
    private AvailableAreaService availableAreaService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ReservationStatusMapper reservationStatusMapper;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @PostMapping("/Create-Reservation")
    public ResponseEntity<?> createReservation( @RequestBody ReservationRequestDto reservationRequestDto) {
        try {
            // Convert UnitRequestDto to Unit
            Reservations reservationsToSave = reservationRequestMapper.toReservation(reservationRequestDto);

            if (reservationRequestDto.getRoomAvailableId() != null) {
                RoomDetails roomDetails = roomDetailsService.getRoomDetailsByUnitIdAndRoomAvailableId(reservationRequestDto.getUnitId(), reservationRequestDto.getRoomAvailableId());
                System.out.println(roomDetails.getId() + "price: " + roomDetails.getNewPrice());
                if (roomDetails != null) {
                    // Update the price based on room details
                    reservationsToSave.setPrice(roomDetails.getNewPrice());
//                    if (roomDetails.getNewPrice() < roomDetails.getOldPrice()) {
//                        reservationsToSave.setPrice(roomDetails.getNewPrice());
//                    } else {
//                        reservationsToSave.setPrice(roomDetails.getOldPrice());
//                    }
                }
            }


            else if (reservationRequestDto.getAvailableAreaId() != null) {
                RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaService.getRoomDetailsByUnitIdAndAvailableAreaId(reservationsToSave.getUnit().getId(), reservationsToSave.getAvailableArea().getId());
                if (roomDetailsForAvailableArea.getId() != null) {
                    // Update the price based on available area details
                    System.out.println("roomDetailsForAvailableArea.getNewPrice(): "+roomDetailsForAvailableArea.getNewPrice());
                    System.out.println("roomDetailsForAvailableArea.getOldPrice(): "+ roomDetailsForAvailableArea.getOldPrice());
                    reservationsToSave.setPrice(roomDetailsForAvailableArea.getNewPrice());
//                    if (roomDetailsForAvailableArea.getNewPrice() < roomDetailsForAvailableArea.getOldPrice()) {
//                        reservationsToSave.setPrice(roomDetailsForAvailableArea.getNewPrice());
//                    } else {
//                        reservationsToSave.setPrice(roomDetailsForAvailableArea.getOldPrice());
//                    }
                }
            }

            else if (reservationRequestDto.getAvailableAreaId() == null && reservationRequestDto.getRoomAvailableId() == null) {

                Unit unit = unitService.getUnitById(reservationRequestDto.getUnitId());

                reservationsToSave.setPrice(unit.getPrice());

            }

            Long userId = reservationRequestDto.getUserId();
                // Save the unit in the database
                Reservations saveReservation = reservationService.saveReservation(userId,reservationsToSave);

                // Check if the reservation has room available or available area

            // Return the unitId in the response body
//            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation Process is successfully with id: " + saveReservation.getId() );

            PushNotificationRequest notificationRequest = new PushNotificationRequest("رسالة من النظام","تم ارسال طلب حجز الوحدة",userId);
            notificationService.processNotificationForGuest(notificationRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(200, "Successful_Reservation.message") );
        } catch (Exception e) {
//            // Log the exception
            logger.error("Error occurred while processing create-reservation request", e);

            System.out.println("Error Message: " + e);
            // Return user-friendly error response
            ApiResponse response = new ApiResponse(400, "Failed_Reservation.message");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseGetId> getReservationById(@PathVariable Long id) {
        Reservations reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        ReservationResponseGetId response = reservationGetByIdMapper.toReservationDto(reservation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Status-Reservation")
    public ResponseEntity<?> getReservationsForUserAndStatus(
            @RequestParam(name = "USER_ID") Long userId,
            @RequestParam(name = "statusUnitId") Long statusUnitId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Reservations> reservations = reservationService.getReservationForUserAndStatus(userId, statusUnitId, pageable);

        if (!reservations.isEmpty()) {
            List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations.getContent());
            return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "no_content.message");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @GetMapping("/Insert-Evaluation-Reservation")
    public ResponseEntity<?> getReservationForEvaluation(
            @RequestParam(name = "USER_ID") Long userId
    ) {
        LocalDate currentDate = LocalDate.now();

        List<Reservations> reservations = reservationService.findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(currentDate, userId);

        if (!reservations.isEmpty()) {
            List<ReservationStatus> reservationRequestDtoList = reservationStatusMapper.toReservationStatusDtoList(reservations);
            return new ResponseEntity<>(reservationRequestDtoList, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse(204, "no_content.message");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

    @PatchMapping("/{reservationId}/set-evaluation")
    public ResponseEntity<?> setEvaluation(@PathVariable Long reservationId, @RequestParam Long evaluationId) {

    Reservations existingReservation = reservationService.getReservationById(reservationId);
    Unit unit = reservationService.findUnitByReservationId(reservationId);

    Long userId = existingReservation.getUser().getId();

    if (existingReservation == null) {
        return ResponseEntity.notFound().build();
    }

    Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);
    if (evaluation == null) {
        return ResponseEntity.badRequest().body("No_Evaluation_Founded.message");
    }

    // Set the Evaluation for the Reservation
    existingReservation.setEvaluation(evaluation);
    existingReservation.setIsEvaluating(true);

    unitService.updateEvaluationsForUnits(unit.getId());

    try {
        // Save the updated Reservation
        reservationService.saveReservation(userId, existingReservation);
        return ResponseEntity.ok().body("Evaluation_Set_Successfully.message");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Evaluation_Set_Failed.message");
    }

}

    @DeleteMapping("Delete/Reservation/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {

        Reservations reservations = reservationRepository.findById(id).orElse(null);

        StatusUnit statusUnit = statusRepository.findById(4L).orElse(null);

        reservations.setStatusUnit(statusUnit);

        reservationRepository.save(reservations);

        ApiResponse response = new ApiResponse(200, "Reservation_deleted.message");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

//    @DeleteMapping("Delete/Reservation/{id}")
//    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
//
//        try {
//            reservationService.updateStatusForReservation(id, 4L);
//            reservationService.deleteUnit(id);
//            ApiResponse response = new ApiResponse(200, "Reservation_Deleted_Successfully.message!");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            ApiResponse response = new ApiResponse(404, "Not_found.message");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }

}
