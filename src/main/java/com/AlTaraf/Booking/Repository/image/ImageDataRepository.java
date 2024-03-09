package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Image.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    List<ImageData> findByUnitIsNull();
//    List<ImageData> findByUnitIsNull();

    List<ImageData> findByUnitId(Long unitId);

    List<ImageData> findByAdsId(Long adsId);
    List<ImageData> findByUserId(Long userId);

    void deleteByUserId(Long userId);
    void deleteByAdsId(Long adsId);

    @Modifying
    @Query("DELETE FROM ImageData i WHERE i.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
