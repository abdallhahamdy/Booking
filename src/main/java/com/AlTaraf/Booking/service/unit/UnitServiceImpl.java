package com.AlTaraf.Booking.service.unit;

import com.AlTaraf.Booking.config.utils.DateUtils;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.repository.image.ImageDataRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import com.AlTaraf.Booking.repository.unit.roomAvailable.RoomAvailableRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;
    // --------------------------------------------------

    // ========= START SAVE UNIT ===========
    public Unit saveUnit(Unit unit) {
        try {
            return unitRepository.save(unit);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw e; // Rethrow the exception or handle it accordingly
        }
    }
    // ========= END SAVE UNIT ===========

    // --------------------------------------------------

    // ========= START GET UNITS BY HOTEL CLASSIFICATION NAMES ===============
    public Page<Unit> getUnitsByHotelClassificationNames(List<String> hotelClassificationNames, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByHotelClassification_NameIn(hotelClassificationNames, pageRequest);
    }
    // ========= END GET UNITS BY HOTEL CLASSIFICATION NAMES ===============

    // --------------------------------------------------

    // ======== START CREATED DATE BETWEEN ==============
    public Page<Unit> getUnitsAddedLastMonth(int page, int size) {
        LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfMonth = LocalDateTime.now();

        Date startDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        PageRequest pageRequest = PageRequest.of(page, size);

        return unitRepository.findByCreatedDateBetween(startDate, endDate, pageRequest);
    }
    // ======== END CREATED DATE BETWEEN ==============

    // --------------------------------------------------

    // ========= START GET FAVORITE UNITS ==========
    public Page<Unit> getFavoriteUnits(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByFavoriteTrue(pageRequest);
    }
    // ========= START GET FAVORITE UNITS ==========

    // --------------------------------------------------

    // ========= START GET UNIT BY ID =============
    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }
    // ========= END GET UNIT BY ID ================

    // --------------------------------------------------

    // ========= START GET UNITS WHICH HIS STATUS IS PENDING =============
    public Page<Unit> getAllPendingUnits(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByStatusUnit_Name("PENDING", pageRequest);
    }
    // ========= END GET UNITS WHICH HIS STATUS IS PENDING =============

    // --------------------------------------------------

    // ======== START GET UNIT BY ACCOMMODATION TYPE ====================
    public Page<Unit> getUnitsByAccommodationTypeName(String accommodationTypeName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return unitRepository.findByAccommodationType_Name(accommodationTypeName, pageRequest);
    }
    // ======== END GET UNIT BY ACCOMMODATION TYPE ====================

    // --------------------------------------------------

    // ========= START DELETE UNIT =============
    @Override
    public void deleteUnit(Long id) {
         unitRepository.deleteById(id);
    }
    // ========= END DELETE UNIT =============

    // --------------------------------------------------

    // ========= START UPDATE IMAGE DATA UNIT =============
    @Override
    public void updateImageDataUnit(Long unitId) {
        // Fetch the Unit by ID
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            // Retrieve associated ImageData entities without a unit
            List<ImageData> imageDataList = imageDataRepository.findByUnitIsNull();

            // Update the unit for each ImageData entity
            for (ImageData imageData : imageDataList) {
                imageData.setUnit(unit);
                imageDataRepository.save(imageData);
            }
        } else {
            // Handle the case when the Unit with the specified ID is not found
            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
        }

    }

    // ========= END UPDATE IMAGE DATA UNIT =============

    // --------------------------------------------------

    // ========= START GET ALL UNITS =============
    @Override
    public Page<Unit> getAllUnits(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }
    // ========= END GET ALL UNITS =============

    // --------------------------------------------------

    // ========= START FILTER UNIT BY NAME =============
    @Override
    public Page<Unit> filterUnitsByName(String nameUnit, Pageable pageable) {
        return unitRepository.findByNameUnitContainingIgnoreCase(nameUnit, pageable);
    }
    // ========= END FILTER UNIT BY NAME =============

    // --------------------------------------------------

    public List<Unit> getUnitsForUserAndStatus(Long userId, String statusUnitName) {
        // Retrieve a List of Units for the given USER_ID and StatusUnit name
        return unitRepository.findAllByUserIdAndStatusUnitName(userId, statusUnitName);
    }
}
