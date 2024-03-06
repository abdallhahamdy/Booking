package com.AlTaraf.Booking.Repository.user;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.User.UserDashboard;
import com.AlTaraf.Booking.Entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDashboardRepository extends JpaRepository<UserDashboard, Long> {



    Optional<UserDashboard> findByEmail(String email);


}

