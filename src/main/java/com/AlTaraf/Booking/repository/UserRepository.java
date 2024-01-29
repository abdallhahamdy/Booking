package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);


    Optional<User> findByPhone(String phone);


    @Query("SELECT COUNT(u.id) > 0 FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.email = :email OR u.phone = :phone) AND r.name IN :roleNames")
    boolean existsByEmailAndRolesOrPhoneNumberAndRoles(
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("roleNames") Set<ERole> roleNames);
}

