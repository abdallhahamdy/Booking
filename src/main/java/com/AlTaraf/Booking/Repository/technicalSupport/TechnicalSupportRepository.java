package com.AlTaraf.Booking.Repository.technicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalSupportRepository extends JpaRepository<TechnicalSupport, Long> {
    void deleteByUser(User user);
}
