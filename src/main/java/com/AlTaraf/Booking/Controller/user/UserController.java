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
import com.AlTaraf.Booking.i18n.I18nUtil;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    I18nUtil i18nUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/Send-OTP")
    public ResponseEntity<?> sendOTP() {

        // Generate and send OTP (you need to implement this logic)
        String otp = userService.generateOtpForUser();
        if (otp != null ) {
            AuthenticationResponse response = new AuthenticationResponse(200, i18nUtil.getMessage("Otp.message"), otp);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(404, i18nUtil.getMessage("Not_found.message"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "phone") String phone,
                                               @RequestParam(value = "roleName") ERole roleName) {

        boolean existsByEmailAndRolesOrPhoneNumberAndRoles = userService.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleName);


        if (existsByEmailAndRolesOrPhoneNumberAndRoles) {
            CheckApiResponse response = new CheckApiResponse(409, i18nUtil.getMessage("Authentication.message"), false);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }

        CheckApiResponse response = new CheckApiResponse(200, "", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/Register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        // Perform user registration
        userService.registerUser(userRegisterDto);

        ApiResponse response = new ApiResponse(200, i18nUtil.getMessage("Registration.message"));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {

            // Check if the phone number exists in the database
            if (!userService.existsByPhone(loginRequest.getPhone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, i18nUtil.getMessage("Duplicate_phone.message")));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            userDetails.setStayLoggedIn(loginRequest.isStayLoggedIn());

            // Get the user from the database
            Optional<User> optionalUser = userService.findByPhone(loginRequest.getPhone());
            User userForDeviceToken = userRepository.findByPhoneForUser(loginRequest.getPhone());
            userForDeviceToken.setDeviceToken(loginRequest.getDeviceToken());

            userRepository.save(userForDeviceToken);

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(500, i18nUtil.getMessage("User_not_found.message")));
            }
            User user = optionalUser.get();

            System.out.println("user.getRole" + user.getRoles());

            // Check if the user's roles match the roles in the request
            Set<String> requestRoles = loginRequest.getRoles();

            System.out.println("requestRoles: " + requestRoles);

            Set<String> userRoles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());

            System.out.println("userRoles: " + userRoles);

            if (Collections.disjoint(userRoles, requestRoles)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, i18nUtil.getMessage("role_is_not_correct.message")));
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
        } catch (Exception e) {
            // Log the exception for debugging
            logger.error("Error during login:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, i18nUtil.getMessage("error_login.message")));
        }
    }

    @PutMapping("/forget-password/{phone}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String phone,
            @RequestBody PasswordResetDto passwordResetDto) {

        // Perform password reset
        try {
            userService.resetPasswordByPhone(phone, passwordResetDto);
            return ResponseEntity.ok(new ApiResponse(200, i18nUtil.getMessage("Password_reset.message")));
        } catch (Exception e) {
            System.out.println("Failed to reset password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(400, i18nUtil.getMessage("Password_reset_failed.message")));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            UserDto userDto = userMapper.INSTANCE.userToUserDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            ApiResponse response = new ApiResponse(404, i18nUtil.getMessage("Not_found.message"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PatchMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @Valid @RequestBody UserEditDto userEditDto) {
        boolean isPhoneAvailable = userService.existsByPhone(userEditDto.getPhone());
        boolean isEmailAvailable = userService.existsByEmail(userEditDto.getEmail());


        try {
            // Retrieve the user by ID
            User existingUser = userService.getUserById(userId);

            // Update the user information conditionally based on non-null values in the UserEditDto
            if (userEditDto.getUsername() != null) {
                existingUser.setUsername(userEditDto.getUsername());
            }

            if (userEditDto.getEmail() != null) {
                if (isEmailAvailable){
                    CheckApiResponse response = new CheckApiResponse(204, i18nUtil.getMessage("Email_taken.message"), false);

                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
                    existingUser.setEmail(userEditDto.getEmail());
                }
            }

            if (userEditDto.getPhone() != null ) {
                if (isPhoneAvailable){
                    CheckApiResponse response = new CheckApiResponse(204, i18nUtil.getMessage("Phone_taken.message"), false);
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

            ApiResponse response = new ApiResponse(200, i18nUtil.getMessage("User_updated.message"));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, i18nUtil.getMessage("Error_updating_user.message")));
        }
    }

    @GetMapping("/checkPhoneNull")
    public ResponseEntity<?> checkUserPhoneNullByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);


        if (user != null && user.getPhone() != null) {
            OauthRequest oauthRequest = new OauthRequest();
            oauthRequest.setPhone(user.getPhone());
            System.out.println("user.getEmail: " + user.getEmail());
            System.out.println("user.getPassword " + oauthRequest.getPassword());

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getPhone(), oauthRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                userDetails.setStayLoggedIn(user.isStayLoggedIn());

                user.setDeviceToken(user.getDeviceToken());

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(i18nUtil.getMessage("Authentication_failed.message"));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,i18nUtil.getMessage("User_not_found.message"))); // User not found or phone number is null
    }

    @PostMapping("/create-for-oauth")
    public ResponseEntity<?> createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam Long cityId,
            @RequestParam ERole role) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + cityId));

        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role));

        // Check if the phone number is unique
        if (userRepository.existsByPhone(phone)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(i18nUtil.getMessage("Phone_taken.message"));
        }

        User userOauth = new User();
        userOauth.setUsername(username);
        userOauth.setEmail(email);
        userOauth.setPhone(phone);
        userOauth.setCity(city);
        userOauth.setPassword(encoder.encode("defaultPassword"));
        userOauth.setRoles(Collections.singleton(userRole));

        userRepository.save(userOauth);

        return ResponseEntity.ok(new ApiResponse(200, i18nUtil.getMessage("Registration.message")));
    }

    }