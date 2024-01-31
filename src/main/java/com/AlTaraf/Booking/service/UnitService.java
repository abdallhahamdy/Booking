package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.ImageData;
import com.AlTaraf.Booking.entity.Unit;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UnitService {
    Unit saveUnit(Unit unit, List<ImageData> imageDataList);
    List<Unit> getAllUnits();
    Unit getUnitById(Long id);
    void deleteUnitById(Long id);
}
