package com.AlTaraf.Booking.repository.unit;

import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    Page<Unit> findByFavoriteTrue(Pageable pageable);

    Page<Unit> findByHotelClassification_NameIn(List<String> hotelClassificationNames, Pageable pageable);

    Page<Unit> findByCreatedDateBetween(Date startOfDay, Date endOfDay, Pageable pageable);

    Page<Unit> findByStatusUnit_Name(String name, Pageable pageable);

    Page<Unit> findByAccommodationType_Name(String accommodationTypeName, Pageable pageable);

//    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

    List<Unit> findAllByUserIdAndStatusUnitName(Long userId, String statusUnitName);

    Page<Unit> findByUser_IdAndFavorite(Long userId, boolean favorite, Pageable pageable);

    List<Unit> findByCity(City city);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%'))")
    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

}