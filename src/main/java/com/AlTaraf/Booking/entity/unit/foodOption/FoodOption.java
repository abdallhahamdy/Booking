package com.AlTaraf.Booking.entity.unit.foodOption;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FOOD_OPTION_NAME")
    private String name;

    @Column(name = "FOOD_OPTION_ARABIC_NAME")
    private String arabicName;

    public FoodOption(Long id) {
        this.id = id;
    }
}
