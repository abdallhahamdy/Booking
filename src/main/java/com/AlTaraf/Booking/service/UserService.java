package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(UserRegisterDto userRegisterDto);
//    User updateUser(Long id, UserRegisterDto userRegisterDto);
//    UserRegisterDto getUserById(Long id);
//    List<UserRegisterDto> getAllUsers();
//    void deleteUser(Long id);

    String generateOtpForUser ();
}

