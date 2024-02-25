package com.AlTaraf.Booking.Service.user;

import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;

import java.util.Optional;
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

    Optional<User> findByPhone(String phone);

}
