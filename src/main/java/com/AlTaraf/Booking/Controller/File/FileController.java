package com.AlTaraf.Booking.Controller.File;


import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.File.FileForPdf;
import com.AlTaraf.Booking.Entity.File.FileForProfile;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.File.FileResponseMessage;
import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Service.File.FileStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload-file-for-unit")
    public ResponseEntity<?> uploadImages(@RequestParam("files") List<MultipartFile> files, @RequestParam("userId") Long userId) {
        List<ImageUploadResponse> responses = new ArrayList<>();
        String message = "";

        try {
            for (MultipartFile file : files) {
                storageService.storeForUnit(file, userId);
                System.out.println("I'm here");
            }

            message = "Uploaded files successfully: " + files.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new FileResponseMessage(message));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new FileResponseMessage(message));
        }
    }

    @PostMapping("/upload-file-for-ads")
    public ResponseEntity<?> uploadImagesForAds(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("unitId") Long unitId) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForAds(file, userId, unitId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201,"Successful_Upload.message " + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }

    @PostMapping("/upload-file-for-pds")
    public ResponseEntity<?> uploadImagesForPdf(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForPdf(file, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201,"Successful_Upload.message " + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }


    @PostMapping("/upload-file-for-profile")
    public ResponseEntity<?> uploadImagesForProfile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "image_background", required = false) Boolean image_background) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            storageService.storeForProfile(file, userId, image_background);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201,"Successful_Upload.message " + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }

    @PostMapping("/set-file-for-unit")
    public ResponseEntity<String> updateFileForUnit(
            @RequestParam("unitId") Long unitId,
            @RequestParam("userId") Long userId) {
        storageService.setFileForUnit(unitId, userId);
        return new ResponseEntity<>("Image_Updated.message " + unitId, HttpStatus.OK);
    }


    @PostMapping("/set-file-for-ads")
    public ResponseEntity<String> updateFileForAds(
            @RequestParam("adsId") Long adsId,
            @RequestParam("userId") Long userId){
        storageService.setFileForAds(adsId, userId);
        return new ResponseEntity<>("Image_Updated.message " + adsId, HttpStatus.OK);
    }

    @GetMapping("/files-for-unit/{id}")
    public ResponseEntity<byte[]> getFileForUnit(@PathVariable String id) {
        FileForUnit fileForUnit = storageService.getFileForUnit(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForUnit.getName() + "\"")
                .body(fileForUnit.getData());
    }

    @GetMapping("/file-for-ads/{id}")
    public ResponseEntity<byte[]> getFileForAds(@PathVariable String id) {
        FileForAds fileForAds = storageService.getFileForAds(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForAds.getName() + "\"")
                .body(fileForAds.getData());
    }

    @GetMapping("/file-for-pdf/{id}")
    public ResponseEntity<byte[]> getFileForPdf(@PathVariable String id) {
        FileForPdf fileForPdf = storageService.getFileForPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForPdf.getName() + "\"")
                .body(fileForPdf.getData());
    }

    @GetMapping("/file-for-profile/{id}")
    public ResponseEntity<byte[]> getFileForProfile(@PathVariable String id) {
        FileForProfile fileForPdf = storageService.getFileForProfile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileForPdf.getName() + "\"")
                .body(fileForPdf.getData());
    }

    @Transactional
    @DeleteMapping("/delete-file-for-unit")
    public ResponseEntity<String> deleteFileForUnit(
            @RequestParam(required = false) Long unitId){

        if (unitId != null) {
            storageService.deleteFilesForUnit(unitId);
        }

        return ResponseEntity.ok("Delete_Image.message");
    }

    @Transactional
    @DeleteMapping("/delete-Image-Data-For-Ads")
    public ResponseEntity<String> deleteImageDataForAds(
            @RequestParam(required = false) Long adsId){

        if (adsId != null) {
            storageService.deleteFileForAds(adsId);
        }

        return ResponseEntity.ok("Delete_Image.message");
    }

    @Transactional
    @DeleteMapping("/delete-profile-user")
    public ResponseEntity<?> deleteImagesProfile(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "image_background", required = false) Boolean image_background) throws IOException {
        List<ImageUploadResponse> responses = new ArrayList<>();

        if (userId != null) {
            storageService.deleteByUserIdAndImageBackgroundIsNull(userId);
        }

        if (image_background != null) {
            storageService.deleteByUserIdAndImageBackgroundTrue(userId);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200,"Delete_Image.message"));
    }
}