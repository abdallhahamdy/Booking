package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    String generateOtpForUser ();

    Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String email, String phone, Collection<Long> roleIds);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    User registerUser(UserRegisterDto userRegisterDto);

    User getUserById(Long id);

//    User updateUser(Long id, UserRegisterDto userRegisterDto);
//    UserRegisterDto getUserById(Long id);
//    List<UserRegisterDto> getAllUsers();
//    void deleteUser(Long id);

}

