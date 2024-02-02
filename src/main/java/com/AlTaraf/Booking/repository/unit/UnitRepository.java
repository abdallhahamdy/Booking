package com.AlTaraf.Booking.repository.unit;

import com.AlTaraf.Booking.entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    // ========== REPOSITORY FOR FAVORITE ==============
    Page<Unit> findByFavoriteTrue(Pageable pageable);

    // ========== REPOSITORY FOR HOTEL CLASSIFICATION NAMES ==============
    Page<Unit> findByHotelClassification_HotelClassificationNameIn(List<String> hotelClassificationNames, Pageable pageable);

}