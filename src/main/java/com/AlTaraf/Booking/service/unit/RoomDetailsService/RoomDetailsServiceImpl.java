package com.AlTaraf.Booking.service.unit.RoomDetailsService;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import com.AlTaraf.Booking.repository.unit.roomAvailable.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailsServiceImpl implements RoomDetailsService{

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private RoomAvailableRepository roomAvailableRepository;

    @Override
    public void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetails roomDetails) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));

        RoomAvailable roomAvailable = roomAvailableRepository.findById(roomAvailableId)
                .orElseThrow(() -> new RuntimeException("RoomAvailable not found with ID: " + roomAvailableId));

        roomDetails.setUnit(unit);
        roomDetails.setRoomAvailable(roomAvailable);

        roomDetailsRepository.save(roomDetails);
    }

    @Override
    public RoomDetails getRoomDetailsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId) {
        return roomDetailsRepository.findByUnitIdAndRoomAvailableId(unitId, roomAvailableId);
    }
}
