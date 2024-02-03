package com.AlTaraf.Booking.controller.image;

import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.service.image.ImageDataService;
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

    @GetMapping("/by-unit/{unitId}")
    public ResponseEntity<List<ImageData>> getImagesByUnitId(@PathVariable Long unitId) {
        List<ImageData> images = imageDataService.getImagesByUnitId(unitId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
