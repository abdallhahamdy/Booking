package com.AlTaraf.Booking.Repository.unit;

import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    Page<Unit> findByFavoriteTrue(Pageable pageable);

    Page<Unit> findByHotelClassification_NameIn(List<String> hotelClassificationNames, Pageable pageable);

    @Query("SELECT u FROM Unit u JOIN u.evaluation e ORDER BY e.score DESC")
    Page<Unit> findByEvaluationInOrderByEvaluationScoreDesc(Pageable pageable);

    Page<Unit> findByCreatedDateBetween(Date startOfDay, Date endOfDay, Pageable pageable);

    Page<Unit> findByStatusUnit_Name(String name, Pageable pageable);

    Page<Unit> findByAccommodationType_Name(String accommodationTypeName, Pageable pageable);

//    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

    List<Unit> findAllByUserIdAndStatusUnitName(Long userId, String statusUnitName);

    Page<Unit> findByUser_IdAndFavorite(Long userId, boolean favorite, Pageable pageable);

    Page<Unit> findByCity(City city, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%'))")
    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%'))")
    List<Unit> findByNameUnitContainingIgnoreCaseForMap(String nameUnit );

    @Query("SELECT u FROM Unit u WHERE u.unitType.id = :unitTypeId")
    Page<Unit> findByUnitType_Id(Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.unitType.id = :unitTypeId")
    List<Unit> findByUnitType_IdForMap(@Param("unitTypeId") Long unitTypeId);

    Page<Unit> findByUserId(Long userId, Pageable pageable);

    List<Unit> findAll(Specification<Unit> spec);

    @Query("SELECT u FROM Unit u JOIN u.roomAvailableSet ra WHERE ra = :roomAvailable")
    List<Unit> findByRoomAvailable(@Param("roomAvailable") RoomAvailable roomAvailable);


    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND u.unitType.id = :unitTypeId")
    Page<Unit> findByNameUnitAndUnitType(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId, Pageable pageable );

    @Query("SELECT u FROM Unit u WHERE u.nameUnit = :nameUnit AND u.unitType.id = :unitTypeId")
    List<Unit> findByNameUnitAndUnitTypeForMap(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId );

//    Page<Unit> findByRoomAvailableSet_NameContainingIgnoreCase(String roomAvailableName, Pageable pageable);

    @Query("SELECT u FROM Unit u JOIN u.roomAvailableSet ra WHERE LOWER(ra.arabicName) LIKE LOWER(concat('%', :roomAvailableName, '%'))")
    Page<Unit> findByRoomAvailableName(@Param("roomAvailableName") String roomAvailableName, Pageable pageable);

    @Query("SELECT u FROM Unit u INNER JOIN u.roomAvailableSet ra WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND LOWER(ra.arabicName) LIKE LOWER(concat('%', :roomAvailableName, '%'))")
    Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable);

    @Query("SELECT u FROM Unit u JOIN u.availableAreaSet ra WHERE LOWER(ra.arabicName) LIKE LOWER(concat('%', :availableAreaName, '%'))")
    Page<Unit> findByAvailableAreaName(@Param("availableAreaName") String availableAreaName, Pageable pageable);

    @Query("SELECT u FROM Unit u INNER JOIN u.availableAreaSet ra WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND LOWER(ra.arabicName) LIKE LOWER(concat('%', :availableAreaName, '%'))")
    Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable);

}