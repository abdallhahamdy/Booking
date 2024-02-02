package com.AlTaraf.Booking.service.unit.hotelClassification;

import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.repository.unit.HotelClassification.HotelClassificationRepository;
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
