package com.AlTaraf.Booking.entity.Ads;

import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ADS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADS_ID")
    private Long id;

    @OneToMany(mappedBy = "ads", fetch = FetchType.LAZY)
    private List<ImageData> images;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;



}
