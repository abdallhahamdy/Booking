package com.AlTaraf.Booking.Controller.image;

import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Repository.image.ImageDataForAdsRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataProfileRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Service.image.ImageDataService;
import com.AlTaraf.Booking.Service.unit.UnitService;
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
@RequestMapping("/images")
public class ImageDataController {

    @Autowired
    private ImageDataService imageDataService;

    @Autowired
    UnitService unitService;

    @Autowired
    ImageDataRepository imageDataRepository;

    @Autowired
    ImageDataForAdsRepository imageDataForAdsRepository;

    @Autowired
    ImageDataProfileRepository imageDataProfileRepository;

    @PostMapping("/unit")
    public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files, @RequestParam("userId") Long userId) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                ImageUploadResponse response = imageDataService.uploadImage(file, userId);
                responses.add(response);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(201,"Successful_Upload.message " + responses));
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }

    @PostMapping("/Ads")
    public ResponseEntity<?> uploadImagesForAds(@RequestParam("images") MultipartFile file, @RequestParam("userId") Long userId) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            ImageUploadResponse response = imageDataService.uploadImageForAds(file, userId);
            responses.add(response);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(responses);
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }

    @PostMapping("/profile-user")
    public ResponseEntity<?> uploadImagesProfile(
            @RequestParam("images") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "image_background", required = false) Boolean image_background
    ) {
        List<ImageUploadResponse> responses = new ArrayList<>();

        try {
            ImageUploadResponse response = imageDataService.uploadImageProfile(file, userId, image_background);
            responses.add(response);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(responses);
        } catch (IOException e) {
            System.out.println("Exception Upload Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed_Upload.message ");
        }
    }
    @Transactional
    @DeleteMapping("/delete-profile-user")
    public ResponseEntity<?> deleteImagesProfile(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "image_background", required = false) Boolean image_background) throws IOException {
        List<ImageUploadResponse> responses = new ArrayList<>();

        if (userId != null) {
            imageDataProfileRepository.deleteByUserIdAndImageBackgroundIsNull(userId);
        }

        if (image_background != null) {
            imageDataProfileRepository.deleteByUserIdAndImageBackgroundTrue(userId);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200,"Delete_Image.message"));
    }

    @PostMapping("/update-imagedata-for-unit")
    public ResponseEntity<String> updateImageDataForUnit(
            @RequestParam("unitId") Long unitId,
            @RequestParam("userId") Long userId) {
        unitService.updateImageDataUnit(unitId, userId);
        return new ResponseEntity<>("Image_Updated.message " + unitId, HttpStatus.OK);
    }


    @PostMapping("/update-imagedata-for-ads")
    public ResponseEntity<String> updateImageDataForAds(
            @RequestParam("adsId") Long adsId,
            @RequestParam("userId") Long userId){
        unitService.updateImageDataAds(adsId, userId);
        return new ResponseEntity<>("Image_Updated.message " + adsId, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete-Image-Data")
    public ResponseEntity<String> deleteImageData(
            @RequestParam(required = false) Long unitId){

        if (unitId != null) {
            imageDataRepository.deleteByUnitId(unitId);
        }

        return ResponseEntity.ok("Delete_Image.message");
    }

    @Transactional
    @DeleteMapping("/delete-Image-Data-For-Ads")
    public ResponseEntity<String> deleteImageDataForAds(
            @RequestParam(required = false) Long adsId){

        if (adsId != null) {
            imageDataForAdsRepository.deleteByAdsId(adsId);
        }

        return ResponseEntity.ok("Delete_Image.message");
    }

}
