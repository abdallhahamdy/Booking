package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    Page<Notifications> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notifications n WHERE n.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
