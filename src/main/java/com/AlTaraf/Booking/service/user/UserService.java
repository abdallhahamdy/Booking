package com.AlTaraf.Booking.service.user;

import com.AlTaraf.Booking.dto.User.UserRegisterDto;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.enums.ERole;
import com.AlTaraf.Booking.payload.request.PasswordResetDto;

import java.util.Set;

public interface UserService {

    String generateOtpForUser ();

    Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String email, String phone, Set<ERole> roleNames);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    User registerUser(UserRegisterDto userRegisterDto);

    User getUserById(Long id);

    void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto);

    void updateUser(User user);
//    User updateUser(Long id, UserRegisterDto userRegisterDto);
//    UserRegisterDto getUserById(Long id);
//    List<UserRegisterDto> getAllUsers();
//    void deleteUser(Long id);

}

