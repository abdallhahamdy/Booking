package com.AlTaraf.Booking.repository.unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    // ========== REPOSITORY FOR FAVORITE ==============
    Page<Unit> findByFavoriteTrue(Pageable pageable);

    // ========== REPOSITORY FOR HOTEL CLASSIFICATION NAMES ==============
    Page<Unit> findByHotelClassification_NameIn(List<String> hotelClassificationNames, Pageable pageable);

    // =========== REPOSITORY FOR CREATED DATE BETWEEN ==================
    Page<Unit> findByCreatedDateBetween(Date startOfDay, Date endOfDay, Pageable pageable);

    // =========== REPOSITORY FOR GET UNIT BY STATUS UNIT NAME ==================
    Page<Unit> findByStatusUnit_Name(String name, Pageable pageable);

    Page<Unit> findByAccommodationType_Name(String accommodationTypeName, Pageable pageable);

    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);


}