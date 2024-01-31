package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.config.ImageConfig;
import com.AlTaraf.Booking.entity.ImageData;
import com.AlTaraf.Booking.entity.Unit;
import com.AlTaraf.Booking.repository.ImageDataRepository;
import com.AlTaraf.Booking.repository.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UnitServiceImpl  {
    @Autowired
    UnitRepository unitRepository;

    @Autowired
    ImageDataRepository imageDataRepository;

    @Transactional
    public Unit saveUnit(Unit unit,   List<MultipartFile> imageDataList) {
        // Ensure the association is set for each ImageData in the list
        if (unit.getImageDataList() != null) {
            unit.getImageDataList().forEach(imageData -> imageData.setUnit(unit));
        }

        // Compress and set image data for each file
        for (MultipartFile file : imageDataList) {
            try {
                byte[] fileBytes = file.getBytes();

                ImageData imageData = new ImageData();
                imageData.setName(file.getOriginalFilename());
                imageData.setType(file.getContentType());

                // Compress image data before saving
                byte[] compressedImageData = ImageConfig.compressImage(fileBytes);
                imageData.setImageData(compressedImageData);

                imageData.setUnit(unit);

                unit.getImageDataList().add(imageData);
            } catch (IOException e) {
                // Handle the exception (e.g., log or throw a custom exception)
                e.printStackTrace();
                throw new RuntimeException("Error processing image data");
            }
        }

        // Save the Unit with associated compressed ImageData objects
        Unit savedUnit = unitRepository.save(unit);

        return savedUnit;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    // Add other methods as needed

    public void deleteUnitById(Long id) {
        unitRepository.deleteById(id);
    }
}
