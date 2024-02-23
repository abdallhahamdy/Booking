package com.AlTaraf.Booking.Controller.Calender;

import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Mapper.Calender.ReserveDateMapper;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calender")
public class ReserveDateController {
    @Autowired
    private ReserveDateRepository reserveDateRepository;

    @Autowired
    ReserveDateMapper reserveDateMapper;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @PostMapping
    public ResponseEntity<?> createReserveDate(@RequestBody ReserveDateDto reserveDateRequest) {
        try {
            ReserveDate reserveDate = ReserveDateMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);
            ReserveDate savedReserveDate = reserveDateRepository.save(reserveDate);
            return ResponseEntity.ok("Reserve date created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reserve date: " + e.getMessage());
        }
    }

    @GetMapping("/{roomDetailsForAvailableAreaId}/{unitId}")
    public ResponseEntity<?> getReserveDatesByRoomDetailsForAvailableAreaIdAndUnitId(@PathVariable Long roomDetailsForAvailableAreaId, @PathVariable Long unitId) {
        System.out.println("I'm Outside Bro");

        try {
            System.out.println("Im here inside Try Room");
            boolean roomNumberZeroExists = roomDetailsForAvailableAreaRepository.existsByRoomNumberZero();
            if (roomNumberZeroExists) {
                List<ReserveDate> reserveDates = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
                List<ReserveDateDto> reserveDateRequests = reserveDates.stream()
                        .map(ReserveDateMapper.INSTANCE::reserveDateToReserveDateRequest)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateRequests);

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(205, "Room Still Available"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get reserve dates: " + e.getMessage());
        }
    }
    }
