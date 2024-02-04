package com.AlTaraf.Booking.service.foodOption;

import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.repository.unit.foodOption.FoodOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodOptionServiceImpl implements FoodOptionService{

    @Autowired
    FoodOptionRepository foodOptionRepository;

    @Override
    public List<FoodOption> getAllFoodOption() {
        return foodOptionRepository.findAll();
    }
}