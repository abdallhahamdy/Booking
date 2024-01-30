package com.AlTaraf.Booking.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME_UNIT")
    private String nameUnit;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<ImageData> imageDataList;
    public Unit() {
    }

    public Unit(Long id, String nameUnit, String description, int adultsAllowed, int childrenAllowed, List<ImageData> imageDataList) {
        this.id = id;
        this.nameUnit = nameUnit;
        this.description = description;
        this.adultsAllowed = adultsAllowed;
        this.childrenAllowed = childrenAllowed;
        this.imageDataList = imageDataList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdultsAllowed() {
        return adultsAllowed;
    }

    public void setAdultsAllowed(int adultsAllowed) {
        this.adultsAllowed = adultsAllowed;
    }

    public int getChildrenAllowed() {
        return childrenAllowed;
    }

    public void setChildrenAllowed(int childrenAllowed) {
        this.childrenAllowed = childrenAllowed;
    }

    public List<ImageData> getImageDataList() {
        return imageDataList;
    }

    public void setImageDataList(List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
    }

    public void addImageData(ImageData imageData) {
        if (imageDataList == null) {
            imageDataList = new ArrayList<>();
        }
        imageDataList.add(imageData);
        imageData.setUnit(this); // Set the unit for the imageData
    }
}
