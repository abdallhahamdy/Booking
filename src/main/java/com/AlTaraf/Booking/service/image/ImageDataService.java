package com.AlTaraf.Booking.service.image;

import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageDataService {
    ImageUploadResponse uploadImage(MultipartFile file) throws IOException;

//    List<ImageData> getImagesByUnitId(Long unitId);

}
