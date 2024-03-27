package com.AlTaraf.Booking.Repository.user;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") Long userId);

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

    @Query("SELECT u FROM User u WHERE u.phone = :phone")
    User findByPhoneForUser(@Param("phone") String phone);

    User findByEmail(String email);


    @Query("SELECT COUNT(u.id) = 1 FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.phone = :phone) AND r.name = :roleName")
    boolean existsByEmailAndPhoneNumberAndRole(
//            @Param("email") String email,
            @Param("phone") String phone,
            @Param("roleName") ERole roleName);

    Page<User> findAllByRolesName(ERole roleName, Pageable pageable);

    Page<User> findAllByRolesNameAndUsernameAndPhone(
            ERole roleName, String username, String phone, Pageable pageable);

    Page<User> findByUsername(String username, Pageable pageable);

    Page<User> findByUsernameAndRolesName(String username, ERole roleName, Pageable pageable);

    Page<User> findAllByPhone(String phone, Pageable pageable);

    Page<User> findAllByPhoneAndRolesName(String phone, ERole roleName, Pageable pageable);

//    @Query("SELECT u FROM User u WHERE u.id NOT IN " +
//            "(SELECT ur.user.id FROM UserRole ur WHERE ur.role.name = 'ROLE_ADMIN' OR ur.role.name = 'ROLE_SERVICE')")
//    Page<User> findAllExceptAdminAndService(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRolesName(@Param("roleName") ERole roleName);

}

