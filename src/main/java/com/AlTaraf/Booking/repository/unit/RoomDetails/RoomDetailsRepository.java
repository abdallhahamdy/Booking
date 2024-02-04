package com.AlTaraf.Booking.repository.unit.RoomDetails;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {

}
