package com.AlTaraf.Booking.Service.File;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForAdsRepository;
import com.AlTaraf.Booking.Repository.File.FileForPdfRepository;
import com.AlTaraf.Booking.Repository.File.FileForProfileRepository;
import com.AlTaraf.Booking.Repository.File.FileForUnitRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Autowired
    private FileForUnitRepository fileForUnitRepository;

    @Autowired
    private FileForAdsRepository fileForAdsRepository;

    @Autowired
    private FileForPdfRepository fileForPdfRepository;

    @Autowired
    private FileForProfileRepository fileForProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Override
    public FileForUnit storeForUnit(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForUnit fileForUnit = new FileForUnit(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        fileForUnit.setUser(user);
         fileForUnitRepository.save(fileForUnit);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/files-for-unit/")
                .path(fileForUnit.getId())
                .toUriString();

        fileForUnit.setFileDownloadUri(fileDownloadUri);

        return  fileForUnitRepository.save(fileForUnit);
    }

    @Override
    public FileForAds storeForAds(MultipartFile file, Long userId, Long unitId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForAds fileForAds = new FileForAds(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));
        fileForAds.setUnit(unit);
        fileForAds.setUser(user);
        fileForAdsRepository.save(fileForAds);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-ads/")
                .path(fileForAds.getId())
                .toUriString();

        fileForAds.setFileDownloadUri(fileDownloadUri);

        return  fileForAdsRepository.save(fileForAds);
    }

    @Override
    public FileForPdf storeForPdf(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForPdf fileForPdf = new FileForPdf(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        fileForPdf.setUser(user);
        fileForPdfRepository.save(fileForPdf);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-pdf/")
                .path(fileForPdf.getId())
                .toUriString();

        fileForPdf.setFileDownloadUri(fileDownloadUri);

        return  fileForPdfRepository.save(fileForPdf);
    }

    @Override
    public FileForProfile storeForProfile(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileForProfile fileForProfile = new FileForProfile(fileName, file.getContentType(), file.getBytes());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setFileForProfile(fileForProfile);
        fileForProfileRepository.save(fileForProfile);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .scheme("https") // Set the scheme to HTTPS
                .path("/file-for-profile/")
                .path(fileForProfile.getId())
                .toUriString();

        fileForProfile.setFileDownloadUri(fileDownloadUri);

        return  fileForProfileRepository.save(fileForProfile);
    }

    @Override
    public void setFileForUnit(Long unitId, Long userId) {
        Unit unit = unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            List<FileForUnit> fileForUnitList = fileForUnitRepository.findByUserIdAndUnitIsNull(userId);

            for (FileForUnit fileForUnit : fileForUnitList) {
                fileForUnit.setUnit(unit);
                fileForUnitRepository.save(fileForUnit);
            }
        }
        else {
            throw new EntityNotFoundException("Unit not found with ID: " + unitId);
        }
    }

    @Override
    public void setFileForAds( Long adsId, Long userId) {

        Ads ads = adsRepository.findById(adsId).orElse(null);

        if (ads != null) {
            List<FileForAds> fileForAdsList = fileForAdsRepository.findByUserIdAndAdsIsNull(userId);

            for (FileForAds fileForAds : fileForAdsList) {
                fileForAds.setAds(ads);
                fileForAdsRepository.save(fileForAds);
            }
        }
        else {
            throw new EntityNotFoundException("Unit not found with ID: " + adsId);
        }
    }

    @Override
    public void deleteFilesForUnit(Long unitId) {
        fileForUnitRepository.deleteByUnitId(unitId);
    }

    @Override
    public void deleteFileForAds(Long adsId) {
        fileForAdsRepository.deleteByAdsId(adsId);
    }

//    @Override
//    public void deleteByUserIdAndImageBackgroundTrue(Long userId) {
//        fileForProfileRepository.deleteByUserIdAndImageBackgroundTrue(userId);
//    }
//
//    @Override
//    public void deleteByUserIdAndImageBackgroundIsNull(Long userId) {
//        fileForProfileRepository.deleteByUserIdAndImageBackgroundIsNull(userId);
//    }

    @Override
    public FileForUnit getFileForUnit(String id) {
        return fileForUnitRepository.findById(id).get();
    }

    @Override
    public FileForAds getFileForAds(String id) {
        return fileForAdsRepository.findById(id).get();
    }

    @Override
    public FileForPdf getFileForPdf(String id) {
        return fileForPdfRepository.findById(id).get();
    }

    @Override
    public FileForProfile getFileForProfile(String id) {
        return fileForProfileRepository.findById(id).get();
    }
}
