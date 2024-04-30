package com.AlTaraf.Booking.Repository.image;

import com.AlTaraf.Booking.Entity.Image.Pdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PdfRepository extends JpaRepository<Pdf, Long> {
    Pdf findByUserIdAndSentIsNull(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Pdf p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
