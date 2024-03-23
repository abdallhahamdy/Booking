package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageDataForAds;
import com.AlTaraf.Booking.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataForAdsRepository extends JpaRepository<ImageDataForAds, Long> {

    List<ImageDataForAds> findByAdsId(Long adsId);
    List<ImageDataForAds> findByUserId(Long userId);

    void deleteByUserId(Long userId);
//    void deleteByAdsId(Long adsId);


    @Query("SELECT i FROM ImageDataForAds i WHERE i.user.id = :userId AND i.ads IS NULL")
    List<ImageDataForAds> findByUserIdAndAdsIsNull(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM ImageDataForAds i WHERE i.ads.id = :adsId")
    void deleteByAdsId(@Param("adsId") Long adsId);

    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM ImageDataForAds i WHERE i.ads = :ads")
    void deleteImageDataForAdsByAds(@Param("ads") Ads ads);

    @Modifying
    @Query("DELETE FROM ImageData i WHERE i.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
