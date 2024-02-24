package com.AlTaraf.Booking.Controller.Calender;

import com.AlTaraf.Booking.Dto.calender.ReserveDateHallsDto;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Mapper.Calender.ReserveDateHallsMapper;
import com.AlTaraf.Booking.Mapper.Calender.ReserveDateMapper;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateDto;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateForHallsDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
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
    private ReserveDateHallsRepository reserveDateHallsRepository;

    @Autowired
    ReserveDateMapper reserveDateMapper;

    @Autowired
    ReserveDateHallsMapper reserveDateHallsMapper;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @PostMapping("/reserve-date-halls")
    public ResponseEntity<?> createReserveDateForHalls(@RequestBody ReserveDateHallsDto reserveDateHallsDto) {
        try {
            ReserveDateHalls reserveDateHalls = ReserveDateHallsMapper.INSTANCE.toEntity(reserveDateHallsDto);
            ReserveDateHalls savedReserveDate = reserveDateHallsRepository.save(reserveDateHalls);
            return ResponseEntity.ok("Reserve date Halls created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create reserve date Halls: " + e.getMessage());
        }
    }

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

        try {
            boolean roomNumberZeroExists = roomDetailsForAvailableAreaRepository.existsByRoomNumberZero();
            if (roomNumberZeroExists) {
                List<ReserveDate> reserveDates = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitIdAndReserveTrue(roomDetailsForAvailableAreaId, unitId);
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

    @GetMapping("/get-reserve-date-halls/{unitId}")
    public ResponseEntity<?> getReserveDateHallsByUnitId(@PathVariable Long unitId) {
        try {
            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsRepository.findByUnitIdAndReserveIsTrue(unitId);
            List<ReserveDateHallsDto> reserveDateHallsDtoList = reserveDateHallsList.stream()
                    .map(ReserveDateHallsMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reserveDateHallsDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No reserve Date "));
        }
    }

}
