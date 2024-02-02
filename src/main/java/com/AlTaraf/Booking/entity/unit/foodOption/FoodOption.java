package com.AlTaraf.Booking.entity.unit.foodOption;

import jakarta.persistence.*;

@Entity
@Table(name = "food_option")
public class FoodOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FOOD_OPTION_NAME")
    private String foodOptionName;

    @Column(name = "FOOD_OPTION_ARABIC_NAME")
    private String foodOptionArabicName;

    public FoodOption() {
    }

    public FoodOption(Long id, String foodOptionName, String foodOptionArabicName) {
        this.id = id;
        this.foodOptionName = foodOptionName;
        this.foodOptionArabicName = foodOptionArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodOptionName() {
        return foodOptionName;
    }

    public void setFoodOptionName(String foodOptionName) {
        this.foodOptionName = foodOptionName;
    }

    public String getFoodOptionArabicName() {
        return foodOptionArabicName;
    }

    public void setFoodOptionArabicName(String foodOptionArabicName) {
        this.foodOptionArabicName = foodOptionArabicName;
    }
}
