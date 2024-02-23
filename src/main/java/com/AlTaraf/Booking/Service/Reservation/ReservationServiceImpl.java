package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (statusUnitId.equals(2L) && getAvailableAreaByReservations(reservationId) != null) {
            System.out.println("In condition Reservation status");

            Unit unit = findUnitByReservationId(reservationId);
            AvailableArea availableArea = getAvailableAreaByReservations(reservationId);
            RoomDetailsForAvailableArea roomDetailsForAvailableArea = roomDetailsForAvailableAreaRepository.findByUnitIdAndAvailableAreaId(unit.getId(), availableArea.getId());

            int numberRoom = roomDetailsForAvailableArea.getRoomNumber();
            System.out.println("Before number Room: " + numberRoom);
            numberRoom--;
            roomDetailsForAvailableArea.setRoomNumber(numberRoom);
            System.out.println("After number Room: " + numberRoom);

        }

        System.out.println("After condition status Reservations.");

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        reservations.setStatusUnit(statusUnit);
        reservationRepository.save(reservations);
    }

    @Override
    public AvailableArea getAvailableAreaByReservations(Long reservationId) {
        return reservationRepository.findAvailableAreaIdByReservationId(reservationId);
    }

}

