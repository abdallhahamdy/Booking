package com.AlTaraf.Booking.Repository.technicalSupport;

import com.AlTaraf.Booking.entity.TechnicalSupport.TechnicalSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalSupportRepository extends JpaRepository<TechnicalSupport, Long> {
}
