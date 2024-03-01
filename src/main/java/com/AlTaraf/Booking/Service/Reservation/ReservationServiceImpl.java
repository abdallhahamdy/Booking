package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    ReserveDateRepository reserveDateRepository;

    @Autowired
    ReserveDateHallsRepository reserveDateHallsRepository;

    @Override
    public Reservations saveReservation(Reservations reservations) {
        try {
            return reservationRepository.save(reservations);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Reservations getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservations> getReservationsForUserAndStatus(Long userId, String statusUnitName) {
        return reservationRepository.findAllReservationsByUserIdAndStatusUnitName(userId, statusUnitName);
    }

    @Override
    public List<Reservations> findReservationByUnitId(Long unitId) {
        return reservationRepository.findByUnitId(unitId);
    }

    @Override
    public Unit findUnitByReservationId(Long reservationId){
        return reservationRepository.findUnitByReservationId(reservationId);
    }

    @Override
    public void updateStatusForReservation(Long reservationId, Long statusUnitId) {

        Reservations reservations = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        Unit unit = findUnitByReservationId(reservationId);

        if ( unit.getUnitType().getId() == 2 && statusUnitId.equals(2L) ) {
            List<ReserveDateHalls> reserveDateHalls = reserveDateHallsRepository.findByUnitId(unit.getId());

            for ( ReserveDateHalls reserve: reserveDateHalls ) {
                reserve.setReserve(true);
            }

        }

        if ( statusUnitId.equals(2L) && getAvailableAreaByReservations(reservationId) != null ) {

            AvailableArea availableArea = getAvailableAreaByReservations(reservationId);
            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaRepository.findByUnitIdAndAvailableAreaId(unit.getId(), availableArea.getId());

//            List<ReserveDate> reserveDate = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableArea.getId(), unit.getId());

//            for (ReserveDate date : reserveDate ) {
//                System.out.println("set True");
//                date.setReserve(true);
//            }


            int numberRoom = roomDetailsForAvailableArea.getRoomNumber();
            if (numberRoom > 0) {
                numberRoom--;
                roomDetailsForAvailableArea.setRoomNumber(numberRoom);
            }

        }

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        reservations.setStatusUnit(statusUnit);
        reservationRepository.save(reservations);
    }

    @Override
    public AvailableArea getAvailableAreaByReservations(Long reservationId) {
        return reservationRepository.findAvailableAreaIdByReservationId(reservationId);
    }

    @Override
    public Page<Reservations> getReservationForUserAndStatus(Long userId, String statusUnitName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reservationRepository.findByUserIdAndStatusUnitName(userId, statusUnitName, pageRequest  );
    }

    @Override
    public void changeStatusUnitId(Long reservationId, Long newStatusUnitId) {

    }

    @Override
    public void deleteUnit(Long id) {
        reservationRepository.deleteById(id);
    }
}

