package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByPhone(BigInteger phone);


    @Query("SELECT COUNT(u.id) > 0 FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.email = :email OR u.phone = :phone) AND r.role IN :roleNames")
    boolean existsByEmailAndRolesOrPhoneNumberAndRoles(@Param("email") String email,
                                                       @Param("phone") BigInteger phone,
                                                       @Param("roleNames") Collection<String> roleNames);
}

