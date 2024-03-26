package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    Page<Notifications> findByUserId(Long userId, Pageable pageable);

}
