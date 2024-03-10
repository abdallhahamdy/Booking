package com.AlTaraf.Booking.Controller.image;

import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Service.image.ImageDataService;
import com.AlTaraf.Booking.Service.unit.UnitService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageDataController {

    @Autowired
    private ImageDataService imageDataService;

    @Autowired
    UnitService unitService;

    @Autowired
    ImageDataRepository imageDataRepository;

    @PostMapping
    public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files, @RequestParam("userId") Long userId) throws IOException {
        List<ImageUploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            ImageUploadResponse response = imageDataService.uploadImage(file, userId);
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }

    @PostMapping
    public ResponseEntity<?> uploadImagesProfile(@RequestParam("images") List<MultipartFile> files, @RequestParam("userId") Long userId) throws IOException {
        List<ImageUploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            ImageUploadResponse response = imageDataService.uploadImage(file, userId);
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }

//    @PostMapping("/update-imagedata-for-unit")
//    public ResponseEntity<String> updateImageDataForUnit(
//            @RequestParam(value = "unitId", required = false) Long unitId,
//            @RequestParam(value = "adsId", required = false) Long adsId,
//            @RequestParam(value = "userId") Long userId) {
//        try {
//            unitService.updateImageDataUnit(unitId, userId, adsId);
//            return new ResponseEntity<>("ImageData entities updated successfully", HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update ImageData entities: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/update-imagedata-for-unit")
    public ResponseEntity<String> updateImageDataForUnit(
            @RequestParam("unitId") Long unitId,
            @RequestParam(value = "userId") Long userId) {
        unitService.updateImageDataUnit(userId, unitId);
        return new ResponseEntity<>("ImageData entities updated for Unit with ID: " + unitId, HttpStatus.OK);
    }


    @PostMapping("/update-imagedata-for-ads")
    public ResponseEntity<String> updateImageDataForAds(
            @RequestParam(value = "adsId") Long adsId,
            @RequestParam("userId") Long userId){
        unitService.updateImageDataAds(adsId, userId);
        return new ResponseEntity<>("ImageData entities updated for Ads with ID: " + adsId, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete-Image-Data")
    public ResponseEntity<String> deleteImageData(
            @RequestParam(required = false) Long unitId,
           @RequestParam(required = false) Long adsId ){

        if (unitId == null && adsId == null) {
            return ResponseEntity.badRequest().body("At least one parameter should be provided.");
        }

        if (unitId != null) {
            imageDataRepository.deleteByUnitId(unitId);
        }

        if (adsId != null) {
            imageDataRepository.deleteByAdsId(adsId);
        }
//
//        if (userId != null) {
//            imageDataRepository.deleteByUserId(userId);
//        }

        return ResponseEntity.ok("ImageData deleted successfully.");
    }

//    @PostMapping("/update-imagedata-for-ads")
//    public ResponseEntity<String> updateImageDataForAds(@RequestParam("adsId") Long adsId) {
//        unitService.updateImageDataUnit(unitId);
//        return new ResponseEntity<>("ImageData entities updated for Unit with ID: " + unitId, HttpStatus.OK);
//    }
}
