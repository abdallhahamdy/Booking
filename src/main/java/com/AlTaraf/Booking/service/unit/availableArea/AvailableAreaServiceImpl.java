package com.AlTaraf.Booking.service.unit.availableArea;

import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.repository.unit.availableArea.AvailableAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableAreaServiceImpl implements AvailableAreaService {

    @Autowired
    AvailableAreaRepository availableAreaRepository;

    @Override
    public List<AvailableArea> getAllAvailableArea() {
        return availableAreaRepository.findAll();
    }
}
