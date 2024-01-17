package com.AlTaraf.Booking.security.service;

import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrPhone) throws UsernameNotFoundException {
        // Check if the input is a valid phone number (you may want to implement additional validation)
        if (usernameOrPhone.matches("\\d{10}")) {
            // If it's a valid phone number, try to find the user by phone
            User user = userRepository.findByPhone(usernameOrPhone)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone number: " + usernameOrPhone));

            return UserDetailsImpl.build(user);
        }

        // If the input is not a valid phone number, assume it's a username and proceed as before
        User user = userRepository.findByUsername(usernameOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + usernameOrPhone));

        return UserDetailsImpl.build(user);
    }

}