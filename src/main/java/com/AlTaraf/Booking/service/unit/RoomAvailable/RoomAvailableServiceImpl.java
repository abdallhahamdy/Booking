package com.AlTaraf.Booking.service.unit.RoomAvailable;

import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.repository.unit.roomAvailable.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAvailableServiceImpl implements RoomAvailableService {

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    @Override
    public List<RoomAvailable> getAllRoomAvailable() {
        return roomAvailableRepository.findAll();
    }

    @Override
    public List<RoomAvailable> getRoomAvailableByIds(List<Long> roomAvailableIds) {
        return roomAvailableRepository.findAllById(roomAvailableIds);
    }
}
