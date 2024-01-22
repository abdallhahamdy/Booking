package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
    Optional<City> findByArabicCityName(String arabicCityName);

}

