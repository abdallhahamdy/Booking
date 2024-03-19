package com.AlTaraf.Booking.Repository.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findByStatusUnitId(Long statusUnitId);

    List<Ads> findAllAdsByUserIdAndStatusUnitName(Long userId, String statusUnitName);

    List<Ads> findByStatusUnitName(String statusName);

    List<Ads> findByUnitId(Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ads a WHERE a.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    void deleteByUser(User user);

    List<Ads> findByUser(User user);


}
