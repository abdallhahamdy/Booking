package com.AlTaraf.Booking.Repository.role;

import com.AlTaraf.Booking.entity.Role.Role;
import com.AlTaraf.Booking.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}