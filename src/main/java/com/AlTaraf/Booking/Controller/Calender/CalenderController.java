package com.AlTaraf.Booking.Controller.Calender;

import com.AlTaraf.Booking.Dto.calender.ReserveDateHallsDto;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Mapper.Calender.ReserveDateHallsMapper;
import com.AlTaraf.Booking.Mapper.Calender.ReserveDateMapper;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateDto;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calender")
public class CalenderController {
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

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/reserve-date-halls")
    public ResponseEntity<?> createReserveDateForHalls(@RequestBody ReserveDateHallsDto reserveDateHallsDto) {
        try {
            ReserveDateHalls reserveDateHalls = ReserveDateHallsMapper.INSTANCE.toEntity(reserveDateHallsDto);
            ReserveDateHalls savedReserveDate = reserveDateHallsRepository.save(reserveDateHalls);
            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date Halls: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.messagee", null, LocaleContextHolder.getLocale())) );
        }
    }

    @PostMapping
    public ResponseEntity<?> createReserveDate(@RequestBody ReserveDateDto reserveDateRequest) {
        try {
            // Fetch or create the roomDetailsForAvailableArea entity
            RoomDetailsForAvailableArea roomDetails = null;
            if (reserveDateRequest.getRoomDetailsForAvailableAreaId() != null) {
                Optional<RoomDetailsForAvailableArea> roomDetailsOptional = roomDetailsForAvailableAreaRepository.findById(reserveDateRequest.getRoomDetailsForAvailableAreaId());
                roomDetails = roomDetailsOptional.orElse(null);
            }
            // Alternatively, you can create a new RoomDetailsForAvailableArea entity if needed

            // Fetch the Unit entity
            Optional<Unit> unitOptional = unitRepository.findById(reserveDateRequest.getUnitId());
            Unit unit = unitOptional.orElseThrow(() -> new RuntimeException("Unit not found"));

            // Set the Unit in the RoomDetailsForAvailableArea entity
            if (roomDetails != null) {
                roomDetails.setUnit(unit);
            }

            // Map the ReserveDateDto to a ReserveDate entity
            ReserveDate reserveDate = ReserveDateMapper.INSTANCE.reserveDateRequestToReserveDate(reserveDateRequest);

            // Set the roomDetailsForAvailableArea in the ReserveDate entity
            reserveDate.setRoomDetailsForAvailableArea(roomDetails);

            // Save the ReserveDate entity
            ReserveDate savedReserveDate = reserveDateRepository.save(reserveDate);
            return ResponseEntity.ok(new ApiResponse(201, messageSource.getMessage("reserve_date_success.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed Reserve Date: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Reserve_Date_Fail.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/{roomDetailsForAvailableAreaId}/{unitId}")
    public ResponseEntity<?> getReserveDatesByRoomDetailsForAvailableAreaIdAndUnitId(@PathVariable Long roomDetailsForAvailableAreaId, @PathVariable Long unitId) {

        try {

            boolean roomNumberZeroExists = roomDetailsForAvailableAreaRepository.existsByRoomNumberZero();

            if (roomNumberZeroExists) {
                List<ReserveDate> reserveDates = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
                List<ReserveDateDto> reserveDateRequests = reserveDates.stream()
                        .map(ReserveDateMapper.INSTANCE::reserveDateToReserveDateRequest)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(reserveDateRequests);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(205, messageSource.getMessage("Room_Still_Available.message", null, LocaleContextHolder.getLocale())));

        } catch (Exception e) {
            System.out.println("Failed to get Reserve Dates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Failed_Get_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/For-Add-Unit/{roomDetailsForAvailableAreaId}/{unitId}")
    public ResponseEntity<?> getReserveDatesByRoomDetailsForAvailableAreaIdAndUnitIdForUnit(@PathVariable Long roomDetailsForAvailableAreaId, @PathVariable Long unitId) {

        try {

        List<ReserveDate> reserveDates = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableAreaId, unitId);
        List<ReserveDateDto> reserveDateRequests = reserveDates.stream()
                .map(ReserveDateMapper.INSTANCE::reserveDateToReserveDateRequest)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reserveDateRequests);

        } catch (Exception e) {
            System.out.println("Failed to get Reserve Dates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("Failed_Get_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204,  messageSource.getMessage("No_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/For-Add-Unit/get-reserve-date-halls/{unitId}")
    public ResponseEntity<?> getReserveDateHallsByUnitIdForUnit(@PathVariable Long unitId) {
        try {
            List<ReserveDateHalls> reserveDateHallsList = reserveDateHallsRepository.findByUnitIdAndReserve(unitId);
            List<ReserveDateHallsDto> reserveDateHallsDtoList = reserveDateHallsList.stream()
                    .map(ReserveDateHallsMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reserveDateHallsDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("No_Reserve_Date.message", null, LocaleContextHolder.getLocale())));
        }
    }

}
