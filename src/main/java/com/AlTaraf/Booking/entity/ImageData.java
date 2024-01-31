package com.AlTaraf.Booking.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "imageData")
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Transient
    private MultipartFile file;

    public ImageData() {
    }

    public ImageData(Long id, String name, String type, byte[] imageData, Unit unit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public static ImageDataBuilder builder() {
        return new ImageDataBuilder();
    }

    public static class ImageDataBuilder {
        private Long id;
        private String name;
        private String type;
        private byte[] imageData;
        private Unit unit;

        private ImageDataBuilder() {
        }

        public ImageDataBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ImageDataBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ImageDataBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ImageDataBuilder imageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public ImageDataBuilder unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public ImageData build() {
            return new ImageData(id, name, type, imageData, unit);
        }
    }
}