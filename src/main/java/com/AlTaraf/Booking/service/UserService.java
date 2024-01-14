package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.entity.User;

public interface UserService {
    User registerUser(UserRegisterDto userRegisterDto);
}


