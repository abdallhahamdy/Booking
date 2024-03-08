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
@Table(name = "imageData")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String imagePath;

//    @Lob
//    @Column(name = "imagedata", columnDefinition = "LONGBLOB")
//    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonBackReference
    private Unit unit;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "ADS_ID")
    @JsonBackReference
    private Ads ads;

}