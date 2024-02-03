package com.AlTaraf.Booking.entity.unit.foodOption;

import jakarta.persistence.*;

@Entity
@Table(name = "food_option")
public class FoodOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FOOD_OPTION_NAME")
    private String name;

    @Column(name = "FOOD_OPTION_ARABIC_NAME")
    private String arabicName;

    public FoodOption() {
    }

    public FoodOption(Long id, String name, String arabicName) {
        this.id = id;
        this.name = name;
        this.arabicName = arabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }
}
