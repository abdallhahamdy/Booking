package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Image.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    List<ImageData> findByUnitIsNull();
//    List<ImageData> findByUnitIsNull();

    List<ImageData> findByUnitId(Long unitId);
}
