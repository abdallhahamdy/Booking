package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    public Unit saveUnit(Unit unit) {

        return unitRepository.save(unit);
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    // Add other methods as needed

    public void deleteUnitById(Long id) {
        unitRepository.deleteById(id);
    }
}
