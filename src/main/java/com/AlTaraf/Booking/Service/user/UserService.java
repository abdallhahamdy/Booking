package com.AlTaraf.Booking.Service.user;

import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Payload.response.CounterUser;

import java.util.Optional;

public interface UserService {

    String generateOtpForUser ();

    Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String email, String phone, ERole roleNames);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    User registerUser(UserRegisterDto userRegisterDto);

    boolean isDuplicatePhoneNumber( String phone);

    User getUserById(Long id);

    void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto);

    void updateUser(User user);

    Optional<User> findByPhone(String phone);


    void deleteUserAndAssociatedEntities(Long userId);

    User setPackageAdsForUser(Long userId, Long packageAdsId) throws InsufficientFundsException;

    CounterUser getCountUser();
}

