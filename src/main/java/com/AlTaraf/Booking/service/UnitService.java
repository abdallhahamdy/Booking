package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.unit.Unit;

import java.util.List;

public interface UnitService {
    Unit saveUnit(Unit unit);
    List<Unit> getAllUnits();
    Unit getUnitById(Long id);
    void deleteUnitById(Long id);
}
