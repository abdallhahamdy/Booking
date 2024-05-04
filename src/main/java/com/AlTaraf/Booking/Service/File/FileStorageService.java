package com.AlTaraf.Booking.Service.File;

import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    FileForUnit storeForUnit(MultipartFile file, Long userId) throws IOException;
    FileForAds storeForAds(MultipartFile file, Long userId, Long unitId) throws IOException;

    FileForPdf storeForPdf(MultipartFile file, Long userId) throws IOException;
    FileForProfile storeForProfile(MultipartFile file, Long userId, Boolean image_background) throws IOException;

    void setFileForUnit( Long unitId, Long userId);

    void setFileForAds( Long adsId, Long userId);

    void deleteFilesForUnit(Long unitId);
    void deleteFileForAds(Long adsId);

    void deleteByUserIdAndImageBackgroundTrue(@Param("userId") Long userId);

    void deleteByUserIdAndImageBackgroundIsNull(@Param("userId") Long userId);

    FileForUnit getFileForUnit(String id);
    FileForAds getFileForAds(String id);
    FileForPdf getFileForPdf(String id);
    FileForProfile getFileForProfile(String id);
}
