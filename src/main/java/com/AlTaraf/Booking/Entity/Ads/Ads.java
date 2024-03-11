package com.AlTaraf.Booking.Entity.Ads;

import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageDataForAds;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ADS")
@Data
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADS_ID")
    private Long id;

    @OneToMany(mappedBy = "ads", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ImageDataForAds> images;

    @OneToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "PACKAGE_ADS_ID")
    private PackageAds packageAds;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

//    private Boolean showInSlider;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    public Ads() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }
}
