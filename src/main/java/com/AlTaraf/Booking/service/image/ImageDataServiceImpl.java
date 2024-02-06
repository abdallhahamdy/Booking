package com.AlTaraf.Booking.service.image;

import com.AlTaraf.Booking.config.ImageConfig;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.repository.image.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageDataServiceImpl implements ImageDataService{
    @Autowired
    private ImageDataRepository imageDataRepository;

    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {

        imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageConfig.compressImage(file.getBytes())).build());

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());

    }

//    @Override
//    public List<ImageData> getImagesByUnitId(Long unitId) {
//        return imageDataRepository.findByUnitId(unitId);
//    }
//
//    public List<ImageData> getImagesByUnitId(Long unitId) {
//        List<ImageData> dbImages = imageDataRepository.findByUnitId(unitId);
//
//        return dbImages.stream()
//                .map(this::decompressImageData)
//                .collect(Collectors.toList());
//    }

    private ImageData decompressImageData(ImageData dbImage) {
        return ImageData.builder()
                .name(dbImage.getName())
                .type(dbImage.getType())
                .imageData(ImageConfig.decompressImage(dbImage.getImageData()))
                .build();
    }

}

