package com.AlTaraf.Booking.Service.image;

import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageDataForAds;
import com.AlTaraf.Booking.Entity.Image.ImageProfile;
import com.AlTaraf.Booking.Entity.Image.Pdf;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Repository.image.ImageDataForAdsRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataProfileRepository;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import com.AlTaraf.Booking.Repository.image.PdfRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
public class ImageDataServiceImpl implements ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private ImageDataProfileRepository imageDataProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ImageDataForAdsRepository imageDataForAdsRepository;

    @Autowired
    PdfRepository pdfRepository;

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

    public ImageUploadResponse uploadImageForAds(MultipartFile file, Long userId) throws IOException {
        byte[] imageData = file.getBytes();

        String imagePath = null;
        try {
            imagePath = uploadToMinioServer(file.getOriginalFilename(), file.getContentType(), imageData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error uploading image to MinIO server", e);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        ImageDataForAds imageDataEntity = ImageDataForAds.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imagePath(imagePath)
                .user(user)
                .build();

        imageDataForAdsRepository.save(imageDataEntity);

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());
    }

    //
    public ImageUploadResponse uploadImageProfile(MultipartFile file, Long userId, Boolean image_background) throws IOException {
        byte[] imageDataProfile = file.getBytes();

        String imagePath = null;
        try {
            imagePath = uploadToMinioServer(file.getOriginalFilename(), file.getContentType(), imageDataProfile);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error uploading image to MinIO server", e);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        ImageProfile imageDataProfileEntity = ImageProfile.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imagePath(imagePath)
                .user(user)
                .image_background(image_background)
                .build();

        imageDataProfileRepository.save(imageDataProfileEntity);

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());
    }

    public ImageUploadResponse uploadPdf(MultipartFile file, Long userId) throws IOException {
        byte[] imageData = file.getBytes();

        String imagePath = null;
        try {
            imagePath = uploadToMinioServer(file.getOriginalFilename(), file.getContentType(), imageData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error uploading Pdf to MinIO server", e);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Pdf pdf = Pdf.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imagePath(imagePath)
                .user(user)
                .build();

        pdfRepository.save(pdf);

        return new ImageUploadResponse("Pdf uploaded successfully: " +
                file.getOriginalFilename());
    }

    private String uploadToMinioServer(String filename, String contentType, byte[] data) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://env-9854507.tip2.libyanspider.cloud")
                    .credentials("LlolsrYmi3", "RtBvEYOeR6")
                    .build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("ehgzly").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("ehgzly").build());
            } else {
                System.out.println("Bucket 'ehgzly' already exists.");
            }
            // Upload the data to the MinIO server
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("ehgzly")
                    .object(filename)
                    .contentType(contentType) // Set the Content-Type
                    .stream(new ByteArrayInputStream(data), data.length, -1)
                    .build());
            return "http://env-9854507.tip2.libyanspider.cloud/ehgzly/" + filename; // Return the URL of the uploaded image
        } catch (MinioException e) {
            throw new RuntimeException("Error uploading image to MinIO server", e);
        }
    }
}

