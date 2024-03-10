package com.AlTaraf.Booking.Service.image;

import com.AlTaraf.Booking.Config.ImageConfig;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import io.minio.*;
import io.minio.errors.MinioException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageDataServiceImpl implements ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private UserRepository userRepository;

    public ImageUploadResponse uploadImage(MultipartFile file, Long userId) throws IOException {
        byte[] imageData = file.getBytes();

        String imagePath = null;
        try {
            imagePath = uploadToMinioServer(file.getOriginalFilename(), file.getContentType(), imageData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error uploading image to MinIO server", e);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        ImageData imageDataEntity = ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imagePath(imagePath)
                .user(user)
                .build();

        imageDataRepository.save(imageDataEntity);

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());
    }

    private String uploadToMinioServer(String filename, String contentType, byte[] data) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("https://play.min.io")
                    .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
                    .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("tes").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("tes").build());
            } else {
                System.out.println("Bucket 'tes' already exists.");
            }

            // Upload the data to the MinIO server
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("tes")
                    .object(filename)
                    .contentType(contentType) // Set the Content-Type
                    .stream(new ByteArrayInputStream(data), data.length, -1)
                    .build());

            return "https://play.min.io/tes/" + filename; // Return the URL of the uploaded image
        } catch (MinioException e) {
            throw new RuntimeException("Error uploading image to MinIO server", e);
        }
    }
}





//private ImageData decompressImageData(ImageData dbImage) {
//    return ImageData.builder()
//            .name(dbImage.getName())
//            .type(dbImage.getType())
//            .imageData(ImageConfig.decompressImage(dbImage.getImageData()))
//            .build();
//}

