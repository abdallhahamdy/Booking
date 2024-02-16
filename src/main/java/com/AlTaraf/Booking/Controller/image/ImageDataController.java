package com.AlTaraf.Booking.Controller.image;

import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Service.image.ImageDataService;
import com.AlTaraf.Booking.Service.unit.UnitService;
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

    @PostMapping
    public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files) throws IOException {
        List<ImageUploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            ImageUploadResponse response = imageDataService.uploadImage(file);
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }

    @PostMapping("/update-imagedata-for-unit")
    public ResponseEntity<String> updateImageDataForUnit(@RequestParam("unitId") Long unitId) {
        unitService.updateImageDataUnit(unitId);
        return new ResponseEntity<>("ImageData entities updated for Unit with ID: " + unitId, HttpStatus.OK);
    }

//    @PostMapping("/update-imagedata-for-ads")
//    public ResponseEntity<String> updateImageDataForAds(@RequestParam("adsId") Long adsId) {
//        unitService.updateImageDataUnit(unitId);
//        return new ResponseEntity<>("ImageData entities updated for Unit with ID: " + unitId, HttpStatus.OK);
//    }
}
