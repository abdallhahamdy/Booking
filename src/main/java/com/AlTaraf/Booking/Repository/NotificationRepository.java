package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import com.AlTaraf.Booking.Entity.enums.ERole;
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

    @Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE u.id = :userId AND r.id = :roleId")
    Page<Notifications> findAllByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId, Pageable pageable);

//    @Query("SELECT n FROM Notifications n JOIN n.user u JOIN n.role r WHERE (:userId IS NULL OR u.id = :userId) AND (:roleId IS NULL OR r.id = :roleId)")
//    Page<Notifications> findAllByUserIdAndRoleIdOrUserIdIsNull(@Param("userId") Long userId, @Param("roleId") Long roleId, Pageable pageable);

    @Query("SELECT n FROM Notifications n WHERE n.user.id IS NULL AND n.role.id = :roleId")
    Page<Notifications> findAllByRoleId(@Param("roleId") Long roleId, Pageable pageable);

    @Query("SELECT n FROM Notifications n WHERE n.user.id IS NULL AND n.role.id IS NULL")
    Page<Notifications> findAllByRoleIdIsNullAndUserIdIsNull(Pageable pageable);

}
