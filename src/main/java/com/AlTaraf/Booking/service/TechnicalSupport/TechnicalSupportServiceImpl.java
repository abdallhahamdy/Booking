package com.AlTaraf.Booking.service.TechnicalSupport;

import com.AlTaraf.Booking.entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.repository.technicalSupport.TechnicalSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechnicalSupportServiceImpl implements TechnicalSupportService {

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    @Override
    public void saveTechnicalSupport(TechnicalSupport technicalSupport) {
        technicalSupportRepository.save(technicalSupport);
    }
}
