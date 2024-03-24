package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.Hotel.ReserveDateHotel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateHotelRepository extends JpaRepository<ReserveDateHotel, Long> {

    @Query("SELECT rd FROM ReserveDateHotel rd WHERE rd.roomDetails.id = :roomDetailsId AND rd.unit.id = :unitId")
    List<ReserveDateHotel> findByRoomDetailsIdAndUnitId(@Param("roomDetailsId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);


//    @Query("SELECT rd FROM ReserveDate rd WHERE rd.roomDetailsForAvailableArea.id = :roomDetailsForAvailableAreaId AND rd.unit.id = :unitId AND rd.reserve = true")
//    List<ReserveDate> findByRoomDetailsForAvailableAreaIdAndUnitIdAndReserveTrue(@Param("roomDetailsForAvailableAreaId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);

    @Query("SELECT rdh FROM ReserveDateHotel rdh WHERE rdh.unit.id = :unitId")
    List<ReserveDateHotel> findByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateHotel rdh WHERE rdh.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateHotel rdh WHERE rdh.roomDetails.id = :roomDetailsId AND rdh.unit.id = :unitId")
    void deleteByRoomDetailsForAvailableAreaIdAndUnitId(@Param("roomDetailsId") Long roomDetailsId, @Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfoHotel di WHERE di.reserveDateHotel.id = :reserveDateHotelId")
    void deleteDateInfoHotelByReserveDateHotelId(@Param("reserveDateHotelId") Long reserveDateHotelId);

}
