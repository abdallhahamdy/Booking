package com.AlTaraf.Booking.Service.image;

import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageDataService {
    ImageUploadResponse uploadImage(MultipartFile file) throws IOException;

//    List<ImageData> getImagesByUnitId(Long unitId);

}