package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataProfileRepository extends JpaRepository<ImageProfile, Long> {
    List<ImageData> findByUserId(Long userId);
    void deleteByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM ImageProfile ip WHERE ip.user.id = :userId AND ip.image_background = true")
    void deleteByUserIdAndImageBackgroundTrue(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM ImageProfile ip WHERE ip.user.id = :userId AND ip.image_background IS NULL")
    void deleteByUserIdAndImageBackgroundIsNull(@Param("userId") Long userId);
}
