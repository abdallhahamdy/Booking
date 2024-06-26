package com.AlTaraf.Booking.Controller.user;

import com.AlTaraf.Booking.Dto.User.UserDto;
import com.AlTaraf.Booking.Dto.User.UserEditDto;
import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Mapper.UserMapper;
import com.AlTaraf.Booking.Payload.request.LoginRequest;
import com.AlTaraf.Booking.Payload.request.OauthRequest;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Payload.response.*;
import com.AlTaraf.Booking.Repository.cityAndregion.CityRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Security.jwt.JwtUtils;
import com.AlTaraf.Booking.Security.service.UserDetailsImpl;
import com.AlTaraf.Booking.Service.cityAndRegion.CityService;
import com.AlTaraf.Booking.Service.user.UserService;
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
import org.springframework.security.core.AuthenticationException;
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
    CityRepository cityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MessageSource messageSource;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/Send-OTP")
    public ResponseEntity<?> sendOTP(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader) {

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

        // Generate and send OTP (you need to implement this logic)
        String otp = userService.generateOtpForUser();
        if (otp != null ) {
            AuthenticationResponse response = new AuthenticationResponse(200, messageSource.getMessage("otp.message", null, LocaleContextHolder.getLocale()), otp);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(404, messageSource.getMessage("not_found.message", null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "phone") String phone,
                                               @RequestParam(value = "roleName") ERole roleName,
                                               @RequestHeader(name = "Accept-Language", required = false) String acceptLanguageHeader){

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


        boolean existsByEmailAndRolesOrPhoneNumberAndRoles = userService.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleName);


        if (existsByEmailAndRolesOrPhoneNumberAndRoles) {
            CheckApiResponse response = new CheckApiResponse(409, messageSource.getMessage("authentication.message", null, LocaleContextHolder.getLocale()), false);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }

        CheckApiResponse response = new CheckApiResponse(200, "", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
//          String jwt = jwtUtils.generateJwtToken(authentication);

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

    @GetMapping("/checkPhoneNull")
    public ResponseEntity<?> checkUserPhoneNullByEmail(@RequestParam String email,
                                                       @RequestParam String deviceToken,
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

        User user = userRepository.findByEmail(email);


        if (user != null && user.getPhone() != null) {
            OauthRequest oauthRequest = new OauthRequest();
            oauthRequest.setPhone(user.getPhone());

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getPhone(), oauthRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                userDetails.setStayLoggedIn(user.isStayLoggedIn());

                user.setDeviceToken(deviceToken);

                userRepository.save(user);

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(
                        jwtUtils.generateJwtToken(authentication, userDetails.isStayLoggedIn()),
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getPhone(),
                        userDetails.getCity(),
                        roles));
            } catch (AuthenticationException e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageSource.getMessage("authentication_failed.message", null, LocaleContextHolder.getLocale()));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,"not_found.messagee"));
    }

    @PostMapping("/create-for-oauth")
    public ResponseEntity<?> createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam Long cityId,
            @RequestParam ERole role,
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

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + cityId));

        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role));

        // Check if the phone number is unique
        if (userRepository.existsByPhone(phone)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageSource.getMessage("phone_taken.message", null, LocaleContextHolder.getLocale()));
        }

        User userOauth = new User();
        userOauth.setUsername(username);
        userOauth.setEmail(email);
        userOauth.setPhone(phone);
        userOauth.setCity(city);
        userOauth.setPassword(encoder.encode("defaultPassword"));
        userOauth.setRoles(Collections.singleton(userRole));

        userRepository.save(userOauth);

        return ResponseEntity.ok(new ApiResponse(200, messageSource.getMessage("user_successful_created", null, LocaleContextHolder.getLocale())));
    }

}