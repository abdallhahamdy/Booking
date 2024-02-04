package com.AlTaraf.Booking.service.unit.AvailablePeriods;

import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.repository.AvailablePeriods.AvailablePeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailablePeriodsServiceImpl implements AvailablePeriodsService {

    @Autowired
    AvailablePeriodsRepository availablePeriodsRepository;

    @Override
    public List<AvailablePeriods> getAllAvailablePeriods() {
        return availablePeriodsRepository.findAll();
    }
}
