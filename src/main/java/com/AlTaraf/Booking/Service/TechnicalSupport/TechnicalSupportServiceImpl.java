package com.AlTaraf.Booking.Service.TechnicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Repository.technicalSupport.TechnicalSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TechnicalSupportServiceImpl implements TechnicalSupportService {

    @Autowired
    TechnicalSupportRepository technicalSupportRepository;

    @Override
    public void saveTechnicalSupport(TechnicalSupport technicalSupport) {
        technicalSupportRepository.save(technicalSupport);
    }

    @Override
    public Page<TechnicalSupport> getAllTechnicalSupport(Pageable pageable) {
        return technicalSupportRepository.findAll(pageable);
    }


}
