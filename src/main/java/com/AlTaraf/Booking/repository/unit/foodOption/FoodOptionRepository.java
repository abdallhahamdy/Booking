package com.AlTaraf.Booking.repository.unit.foodOption;

import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {

}
