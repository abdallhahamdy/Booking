package com.AlTaraf.Booking.entity.Image;

import com.AlTaraf.Booking.entity.Ads.Ads;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.unit.Unit;
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

    @Lob
    @Column(name = "imagedata", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(name = "ADS_ID")
    private Ads ads;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}