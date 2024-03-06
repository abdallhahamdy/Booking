package com.AlTaraf.Booking.Repository.role;

import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Entity.enums.ERoleDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDashboardRepository extends JpaRepository<RoleDashboard, Long> {

    Optional<RoleDashboard> findByName(ERoleDashboard name);
}