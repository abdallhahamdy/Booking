package com.AlTaraf.Booking.Repository.File;

import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileForProfileRepository extends JpaRepository<FileForProfile, String> {

    List<ImageData> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM FileForProfile fp WHERE fp.user.id = :userId AND fp.imageBackgroundFlag = true")
    void deleteByUserIdAndImageBackgroundTrue(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM FileForProfile fp WHERE fp.user.id = :userId AND fp.imageBackgroundFlag IS NULL")
    void deleteByUserIdAndImageBackgroundIsNull(@Param("userId") Long userId);

    void deleteByUser(User user);

}