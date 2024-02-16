package com.AlTaraf.Booking.Repository.unit.foodOption;

import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {

}
