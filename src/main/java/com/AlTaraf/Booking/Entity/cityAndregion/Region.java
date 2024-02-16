package com.AlTaraf.Booking.Entity.cityAndregion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region", nullable = false, unique = true)
    private String regionName;  // Renamed the property to avoid conflict with the entity name

    @Column(name = "arabic_name")
    private String regionArabicName;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("regions")
    private City city;


    public Region(Long id) {
        this.id = id;
    }
}
