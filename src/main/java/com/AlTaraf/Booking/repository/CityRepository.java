package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    // You can add custom query methods if needed
}

