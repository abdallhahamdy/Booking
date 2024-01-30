package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.config.ImageConfig;
import com.AlTaraf.Booking.entity.ImageData;
import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.repository.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageDataServiceImpl {
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


}

