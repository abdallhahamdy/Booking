package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Repository.Reservation.ReservationRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateHallsRepository;
import com.AlTaraf.Booking.Repository.ReserveDateRepository.ReserveDateRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Reservations saveReservation(Long userId, Reservations reservations) throws InsufficientFundsException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        if (user.getWallet() < reservations.getCommision()) {
            throw new InsufficientFundsException("Failed_Add_Reservation.message");
        }
        return reservationRepository.save(reservations);
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

        if ( (unit.getUnitType().getId() == 2 || unit.getAccommodationType().getId() == 4 ||
                unit.getAccommodationType().getId() == 6 ) && statusUnitId.equals(2L) ) {
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

        if ( statusUnitId.equals(2L) && getRoomAvailableByReservations(reservationId) != null ) {

            RoomAvailable roomAvailable = getRoomAvailableByReservations(reservationId);
            RoomDetails roomDetails = roomDetailsRepository.findByUnitIdAndRoomAvailableId(unit.getId(), roomAvailable.getId());

//            List<ReserveDate> reserveDate = reserveDateRepository.findByRoomDetailsForAvailableAreaIdAndUnitId(roomDetailsForAvailableArea.getId(), unit.getId());

//            for (ReserveDate date : reserveDate ) {
//                System.out.println("set True");
//                date.setReserve(true);
//            }


            int numberRoom = roomDetails.getRoomNumber();
            if (numberRoom > 0) {
                numberRoom--;
                roomDetails.setRoomNumber(numberRoom);
            }

        }

        StatusUnit statusUnit = statusRepository.findById(statusUnitId)
                .orElseThrow(() -> new EntityNotFoundException("StatusUnit not found with id: " + statusUnitId));

        reservations.setStatusUnit(statusUnit);

        User user = reservations.getUser();
        System.out.println("user: " + user.getId());

        if (user.getWallet() > 0) {
            double currentWallentBalance = user.getWallet();
            currentWallentBalance -= reservations.getCommision();
            user.setWallet(currentWallentBalance);
        }
        userRepository.save(user);

        reservationRepository.save(reservations);
    }

    @Override
    public AvailableArea getAvailableAreaByReservations(Long reservationId) {
        return reservationRepository.findAvailableAreaIdByReservationId(reservationId);
    }

    @Override
    public RoomAvailable getRoomAvailableByReservations(Long reservationId) {
        return reservationRepository.findRoomAvailableIdByReservationId(reservationId);
    }

    @Override
    public Page<Reservations> getReservationForUserAndStatus(Long userId, Long statusUnitId , Pageable pageable) {
        return reservationRepository.findByUserIdAndStatusUnitId(userId, statusUnitId, pageable);
    }

    @Override
    public void changeStatusUnitId(Long reservationId, Long newStatusUnitId) {

    }

    @Override
    public void deleteUnit(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Page<Reservations> findByUnitId(Long unitId, Pageable pageable) {
        return reservationRepository.findByUnitId(unitId, pageable);
    }

    @Override
    public Page<Reservations> findByStatusNameAndUnitId(String statusName, Long unitId, Pageable pageable) {
        return reservationRepository.findByStatusNameAndUnitId(statusName, unitId, pageable);
    }

    @Override
    public void setCommissionForAllReservations(Double commission) {
        List<Reservations> reservations = reservationRepository.findAll();
        for (Reservations reservation : reservations) {
            reservation.setCommision(commission);
        }
        reservationRepository.saveAll(reservations);
    }
}

