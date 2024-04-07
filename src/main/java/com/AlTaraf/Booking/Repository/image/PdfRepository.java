package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Image.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<Pdf, Long> {
    Pdf findByUserIdAndSentIsNull(Long userId);
}
