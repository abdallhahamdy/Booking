package com.AlTaraf.Booking.Repository.unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
    RoomDetails findByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

    boolean existsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomDetails rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
