package com.AlTaraf.Booking.repository.image;

import com.AlTaraf.Booking.entity.Image.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    List<ImageData> findByUnitIsNull();

    List<ImageData> findByUnitId(Long unitId);
}
