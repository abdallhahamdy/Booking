package com.AlTaraf.Booking.service.unit.hotelClassification;

import com.AlTaraf.Booking.entity.unit.HotelClassification;
import com.AlTaraf.Booking.repository.unit.HotelClassificationRepository;
import com.AlTaraf.Booking.repository.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelClassificationServiceImpl implements HotelClassificationService{

    @Autowired
    HotelClassificationRepository hotelClassificationRepository;

    @Override
    public List<HotelClassification> getAllHotelClassification() {
        return hotelClassificationRepository.findAll();
    }
}
