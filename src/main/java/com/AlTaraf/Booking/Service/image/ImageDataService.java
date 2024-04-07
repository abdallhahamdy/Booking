package com.AlTaraf.Booking.Service.image;

import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageDataService {
    ImageUploadResponse uploadImage(MultipartFile file, Long userId) throws IOException;
    ImageUploadResponse uploadImageForAds(MultipartFile file, Long userId) throws IOException;
    ImageUploadResponse uploadImageProfile(MultipartFile file, Long userId, Boolean image_background) throws IOException;

    ImageUploadResponse uploadPdf(MultipartFile file, Long userId) throws IOException;

//    List<ImageData> getImagesByUnitId(Long unitId);

}
