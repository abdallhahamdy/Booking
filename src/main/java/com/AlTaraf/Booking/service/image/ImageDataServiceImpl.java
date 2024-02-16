package com.AlTaraf.Booking.service.image;

import com.AlTaraf.Booking.config.ImageConfig;
import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.payload.response.ImageUploadResponse;
import com.AlTaraf.Booking.Repository.image.ImageDataRepository;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageDataServiceImpl implements ImageDataService{
    @Autowired
    private ImageDataRepository imageDataRepository;

    private static final String FTP_SERVER = "168.119.132.11";
    private static final int FTP_PORT = 21;
    private static final String FTP_USER = "jelastic-ftp";
    private static final String FTP_PASSWORD = "4zRpwl30A4";
    private static final String REMOTE_DIRECTORY = "/";

    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {
        byte[] compressedImageData = ImageConfig.compressImage(file.getBytes());

        String imagePath = uploadToFtpServer(file.getOriginalFilename(), compressedImageData);

        imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageConfig.compressImage(file.getBytes()))
                .imagePath(imagePath)
                .build());

//        uploadToFtpServer(file.getOriginalFilename(), compressedImageData);

        return new ImageUploadResponse("Image uploaded successfully: " +
                file.getOriginalFilename());

    }

    private String  uploadToFtpServer(String filename, byte[] data) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SERVER, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            InputStream inputStream = new ByteArrayInputStream(data);

            boolean uploaded = ftpClient.storeFile(REMOTE_DIRECTORY + filename, inputStream);
            inputStream.close();

            if (uploaded) {
                System.out.println("File uploaded successfully to FTP server.");
                return REMOTE_DIRECTORY + filename; // Return the image path
            } else {
                System.out.println("Failed to upload file to FTP server.");
                return null; // Return null if upload fails
            }
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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

