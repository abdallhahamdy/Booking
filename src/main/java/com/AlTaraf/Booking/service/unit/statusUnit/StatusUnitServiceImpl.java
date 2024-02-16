package com.AlTaraf.Booking.service.unit.statusUnit;

import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusUnitServiceImpl implements StatusUnitService {
    @Autowired
    StatusUnitRepository statusUnitRepository;

    @Autowired
    UnitRepository unitRepository;

    @Override
    public List<StatusUnit> getAllStatusUnit() {
        return statusUnitRepository.findAll();
    }

}
