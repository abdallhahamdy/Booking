package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.Unit;
import com.AlTaraf.Booking.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    public Unit saveUnit(Unit unit) {
        // Ensure the association is set for each ImageData in the list
        if (unit.getImageDataList() != null) {
            unit.getImageDataList().forEach(imageData -> imageData.setUnit(unit));
        }

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
