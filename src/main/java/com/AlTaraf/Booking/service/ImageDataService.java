package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageDataService {
    ImageUploadResponse uploadImage(MultipartFile file) throws IOException;

    byte[] getImage(String name);
}
