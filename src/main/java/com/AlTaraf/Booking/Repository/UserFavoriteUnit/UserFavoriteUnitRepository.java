package com.AlTaraf.Booking.Repository.UserFavoriteUnit;

import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFavoriteUnitRepository extends JpaRepository<UserFavoriteUnit, Long> {
    List<UserFavoriteUnit> findByUser(User user);

    @Query("SELECT uf FROM UserFavoriteUnit uf WHERE uf.user.id = :userId")
    Page<UserFavoriteUnit> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(u) > 0 FROM UserFavoriteUnit u WHERE u.user = :user AND u.unit = :unit")
    boolean existsByUserAndUnit(@Param("user") User user, @Param("unit") Unit unit);

}