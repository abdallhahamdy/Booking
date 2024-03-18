package com.AlTaraf.Booking.Repository.user;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.User.UserDashboard;
import com.AlTaraf.Booking.Entity.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT COUNT(u) > 1 FROM User u WHERE u.phone = :phone")
    boolean isDuplicatePhoneNumber(@Param("phone") String phone);

    @Query("SELECT COUNT(u) > 1 FROM User u WHERE u.email = :email")
    boolean isDuplicateEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "JOIN u.roles r " +
            "WHERE u.phone = :phone AND u.id <> :userId")
    boolean existsByPhoneForDifferentUser(@Param("phone") String phone, @Param("userId") Long userId);

//    @Query("SELECT u FROM User u WHERE u.phone = :phone")
//    User findByPhone(@Param("phone") String phone);

    User findByEmail(String email);


    @Query("SELECT COUNT(u.id) = 1 FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.email = :email OR u.phone = :phone) AND r.name = :roleName")
    boolean existsByEmailAndPhoneNumberAndRole(
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("roleName") ERole roleName);

    Page<User> findAllByRolesName(ERole roleName, Pageable pageable);
}

