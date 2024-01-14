package com.AlTaraf.Booking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city",nullable = false, unique = true)
    private String city;
}
