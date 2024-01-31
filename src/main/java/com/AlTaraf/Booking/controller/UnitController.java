package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.entity.ImageData;
import com.AlTaraf.Booking.entity.Unit;
import com.AlTaraf.Booking.service.UnitService;
import com.AlTaraf.Booking.service.UnitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {
    @Autowired
    UnitServiceImpl unitService;

    @PostMapping
    public ResponseEntity<Unit> createUnit(
            @RequestParam("nameUnit") String nameUnit,
            @RequestParam("description") String description,
            @RequestParam("adultsAllowed") int adultsAllowed,
            @RequestParam("imageDataList") List<MultipartFile> imageDataList) {

        // Process image data, convert to byte[], and create ImageData objects
        List<ImageData> processedImageDataList = new ArrayList<>();

        for (MultipartFile file : imageDataList) {
            try {
                byte[] fileBytes = file.getBytes();

                ImageData imageData = new ImageData();
                imageData.setName(file.getOriginalFilename());
                imageData.setType(file.getContentType());
                imageData.setImageData(fileBytes);

                processedImageDataList.add(imageData);
            } catch (IOException e) {
                // Handle the exception (e.g., log or return an error response)
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Create Unit with processed ImageData list
        Unit unit = new Unit();
        unit.setNameUnit(nameUnit);
        unit.setDescription(description);
        unit.setAdultsAllowed(adultsAllowed);
//        unit.setChildrenAllowed(childrenAllowed);
        unit.setImageDataList(processedImageDataList);

        Unit savedUnit = unitService.saveUnit(unit, processedImageDataList);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }
}
