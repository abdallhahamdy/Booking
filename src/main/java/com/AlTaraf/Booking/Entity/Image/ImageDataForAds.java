package com.AlTaraf.Booking.Entity.Image;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "image_data_ads")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDataForAds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String imagePath;

//    @Lob
//    @Column(name = "imagedata", columnDefinition = "LONGBLOB")
//    private byte[] imageData;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADS_ID")
    @JsonBackReference
    private Ads ads;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

}