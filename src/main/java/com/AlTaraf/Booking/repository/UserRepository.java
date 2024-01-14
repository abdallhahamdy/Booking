package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
//    User findByEmail(String email);
    Optional<User> findByPhone(BigInteger phone);

}

