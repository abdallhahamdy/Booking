package com.AlTaraf.Booking.entity.cityAndregion;

import com.AlTaraf.Booking.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false, unique = true)
    private String cityName;  // Renamed the property to avoid conflict with the entity name

    @Column(name = "arabic_name")
    private String arabicCityName;

//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
//    private List<Region> regions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "city")  // Refers to the 'city' property in the User entity
//    @JsonIgnore
//    private Set<User> users = new HashSet<>();


}
