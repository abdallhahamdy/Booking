package com.AlTaraf.Booking.Controller.user;

import com.AlTaraf.Booking.Dto.User.UserDto;
import com.AlTaraf.Booking.Dto.User.UserEditDto;
import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Mapper.UserMapper;
import com.AlTaraf.Booking.Payload.request.LoginRequest;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Payload.response.*;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Security.jwt.JwtUtils;
import com.AlTaraf.Booking.Security.service.UserDetailsImpl;
import com.AlTaraf.Booking.Service.cityAndRegion.CityService;
import com.AlTaraf.Booking.Service.user.UserService;
import com.AlTaraf.Booking.Service.user.otp.OtpService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    OtpService otpService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/sendOtpWhats")
    public ResponseEntity<?> sendOtpWhats(@RequestParam String recipient, @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        return otpService.sendOtpViaWhatsApp(recipient, acceptLanguageHeader);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestParam String recipient, @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        return otpService.sendOtp(recipient, acceptLanguageHeader);
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam String recipient, @RequestParam int otp,
                                         @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {


        return otpService.validateOtp(recipient, otp, acceptLanguageHeader);
    }

    @PostMapping("/Register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto,
                                          @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        Set<ERole> roles = userRegisterDto.getRoles().stream()
                .map(ERole::valueOf)
                .collect(Collectors.toSet());


        boolean existsByEmailAndRolesOrPhoneNumberAndRoles = userService.existsByEmailAndRolesOrPhoneNumberAndRoles(userRegisterDto.getPhoneNumber(), roles);


        if (existsByEmailAndRolesOrPhoneNumberAndRoles) {
            CheckApiResponse response = new CheckApiResponse(409, messageSource.getMessage("authentication.message", null, LocaleContextHolder.getLocale()), false);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }

        // Perform user registration
        userService.registerUser(userRegisterDto);

        ApiResponse response = new ApiResponse(200, messageSource.getMessage("registration.message", null, LocaleContextHolder.getLocale()));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,
                                   @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }

        // Check if the phone number exists in the database
            if (!userService.existsByPhone(loginRequest.getPhone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, messageSource.getMessage("duplicate_phone.message", null, LocaleContextHolder.getLocale())));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            userDetails.setStayLoggedIn(loginRequest.isStayLoggedIn());

            // Get the user from the database
            Optional<User> optionalUser = userService.findByPhone(loginRequest.getPhone());
            User userForDeviceToken = userRepository.findByPhoneForUser(loginRequest.getPhone());
            userForDeviceToken.setDeviceToken(loginRequest.getDeviceToken());

            userRepository.save(userForDeviceToken);

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("user_not_found.message", null, LocaleContextHolder.getLocale())));
            }
            User user = optionalUser.get();

            Set<String> userRoles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());

            Set<String> requestRoles = loginRequest.getRoles();

            if (Collections.disjoint(userRoles, requestRoles)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, messageSource.getMessage("role_is_not_correct.message", null, LocaleContextHolder.getLocale())));
            }

            if (userForDeviceToken.getBan()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse(200, messageSource.getMessage("user_ban.message", null, LocaleContextHolder.getLocale())));
            }

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(
                    jwtUtils.generateJwtToken(authentication, loginRequest.isStayLoggedIn()),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhone(),
                    userDetails.getCity(),
                    roles));

    }

    @PutMapping("/forget-password/{phone}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String phone,
            @RequestBody PasswordResetDto passwordResetDto,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        // Perform password reset
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }
            userService.resetPasswordByPhone(phone, passwordResetDto);
            return ResponseEntity.ok(new ApiResponse(200, messageSource.getMessage("password_reset.message", null, LocaleContextHolder.getLocale())));
        } catch (Exception e) {
            System.out.println("Failed to reset password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, messageSource.getMessage("failed_reset_password.message", null, LocaleContextHolder.getLocale()) ));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id,
                                         @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

        Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

        if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
            try {
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                if (!languageRanges.isEmpty()) {
                    locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                }
            } catch (IllegalArgumentException e) {
                // Handle the exception if needed
                System.out.println("IllegalArgumentException: " + e);
            }
        }
        User user = userService.getUserById(id);

        if (user != null) {
            UserDto userDto = userMapper.INSTANCE.userToUserDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PatchMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId,
                                      @Valid @RequestBody UserEditDto userEditDto,
                                      @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {
        try {
            Locale locale = LocaleContextHolder.getLocale(); // Default to the locale context holder's locale

            if (acceptLanguageHeader != null && !acceptLanguageHeader.isEmpty()) {
                try {
                    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(acceptLanguageHeader);
                    if (!languageRanges.isEmpty()) {
                        locale = Locale.forLanguageTag(languageRanges.get(0).getRange());
                    }
                } catch (IllegalArgumentException e) {
                    // Handle the exception if needed
                    System.out.println("IllegalArgumentException: " + e);
                }
            }

            // Retrieve the user by ID
            User existingUser = userService.getUserById(userId);
            boolean isPhoneAvailable = userService.existsByPhone(userEditDto.getPhone());
            boolean isEmailAvailable = userService.existsByEmail(userEditDto.getEmail());
            // Update the user information conditionally based on non-null values in the UserEditDto
            if (userEditDto.getUsername() != null) {
                existingUser.setUsername(userEditDto.getUsername());
            }

            if (userEditDto.getEmail() != null && !Objects.equals(existingUser.getEmail(), userEditDto.getEmail())) {
                System.out.println("yo");
                if (isEmailAvailable){
                    CheckApiResponse response = new CheckApiResponse(204, messageSource.getMessage("email_taken.message", null, LocaleContextHolder.getLocale()), false);
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
                    existingUser.setEmail(userEditDto.getEmail());
                }
            }

            if (userEditDto.getPhone() != null && !Objects.equals(existingUser.getPhone(), userEditDto.getPhone())) {
                if (isPhoneAvailable){
                    CheckApiResponse response = new CheckApiResponse(204,  messageSource.getMessage("phone_taken.message", null, LocaleContextHolder.getLocale()), false);
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
                    existingUser.setPhone(userEditDto.getPhone());

                }
            }


            if (userEditDto.getPassword() != null) {
                existingUser.setPassword(encoder.encode(userEditDto.getPassword()));
            }

            // Retrieve and set the city based on the provided cityId
            if (userEditDto.getCityId() != null) {
                Optional<City> optionalCity = cityService.getCityById(userEditDto.getCityId());

                if (optionalCity.isPresent()) {
                    // If the City is present, set it to the existing user
                    existingUser.setCity(optionalCity.get());
                } else {
                    // Handle the case where the City is not found
                    throw new RuntimeException("City not found for id: " + userEditDto.getCityId());
                }
            }

            // Save the updated user
            userService.updateUser(existingUser);

            ApiResponse response = new ApiResponse(200, messageSource.getMessage("user_updated.message", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, messageSource.getMessage("error_updating_user.message", null, LocaleContextHolder.getLocale())));
        }
    }

}