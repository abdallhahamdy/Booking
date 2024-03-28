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
import com.AlTaraf.Booking.Payload.response.OauthResponse.OauthResponse;
import com.AlTaraf.Booking.Payload.response.OauthResponse.OauthResponseForSignUp;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

//    @Autowired
//    userMapper userMapper2;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/Send-OTP")
    public ResponseEntity<?> sendOTP() {

        // Generate and send OTP (you need to implement this logic)
        String otp = userService.generateOtpForUser();
        if (otp != null ) {
            AuthenticationResponse response = new AuthenticationResponse(200, "OTP Sent successfully!", otp);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(404, "Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "phone") String phone,
                                               @RequestParam(value = "roleName") ERole roleName) {

        boolean existsByEmailAndRolesOrPhoneNumberAndRoles = userService.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleName);
        boolean isEmailAvailable = userService.existsByEmail(email);
        boolean isPhoneAvailable = userService.existsByPhone(phone);
        boolean isDuplicatePhone = userService.isDuplicatePhoneNumber(phone);

        if (existsByEmailAndRolesOrPhoneNumberAndRoles) {
            CheckApiResponse response = new CheckApiResponse(409, "User with the same email, phone number, and role already exists.", false);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response);
        }
//
//        if (!existsByEmailAndRolesOrPhoneNumberAndRoles && isDuplicatePhone && !isEmailAvailable) {
//            CheckApiResponse response = new CheckApiResponse(409, "Phone is already taken.", false);
//
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(response);
//        }

//        else if (isEmailAvailable) {
//            CheckApiResponse response = new CheckApiResponse(409, "Email is already taken.", false);
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(response);
//        } else if (isPhoneAvailable) {
//            CheckApiResponse response = new CheckApiResponse(409, "Phone is already taken.", false);
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(response);
//        }

        CheckApiResponse response = new CheckApiResponse(200, "", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/Register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        // Perform user registration
        userService.registerUser(userRegisterDto);

        ApiResponse response = new ApiResponse(200, "User Registered Successfully!");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {

            // Check if the phone number exists in the database
            if (!userService.existsByPhone(loginRequest.getPhone())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, "Phone number not registered"));
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
                        .body(new ApiResponse(500, "Error in login. User not found"));
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
                        .body(new ApiResponse(400, "Your role is not correct"));
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Error in login Password is not correct"));
        }
    }

    @PutMapping("/forget-password/{phone}")
    public ResponseEntity<?> resetPassword(
            @PathVariable String phone,
            @RequestBody PasswordResetDto passwordResetDto) {

        // Perform password reset
        try {
            userService.resetPasswordByPhone(phone, passwordResetDto);
            return ResponseEntity.ok(new ApiResponse(200, "Password reset successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(400, "Failed to reset password: " + e.getMessage()));
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            UserDto userDto = userMapper.INSTANCE.userToUserDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            ApiResponse response = new ApiResponse(404, "Not Found!");
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
                    CheckApiResponse response = new CheckApiResponse(204, "Email is already taken.", false);

                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
                    existingUser.setEmail(userEditDto.getEmail());
                }
            }

            if (userEditDto.getPhone() != null ) {
                if (isPhoneAvailable){
                    CheckApiResponse response = new CheckApiResponse(204, "Phone is already taken.", false);
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(response);
                } else {
//                    existingUser.setPhone(userEditDto.getPhone());
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

            ApiResponse response = new ApiResponse(200, "User Updated Successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Error updating user."));
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRegisterDto userRegisterDto) {
//        try {
//            userService.updateUser(id, userRegisterDto);
//            return ResponseEntity.ok("User updated successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating user: " + e.getMessage());
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserRegisterDto> getUserById(@PathVariable Long id) {
//        UserRegisterDto userDto = userService.getUserById(id);
//        return userDto != null
//                ? ResponseEntity.ok(userDto)
//                : ResponseEntity.notFound().build();
//    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllUsers() {
//        List<UserRegisterDto> users = userService.getAllUsers();
//        if (!users.isEmpty()) {
//            return ResponseEntity.ok(users);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content for Roles!");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//        }
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        try {
//            userService.deleteUser(id);
//            ApiResponse response = new ApiResponse(200, "Role deleted successfully!");
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            ApiResponse response = new ApiResponse(404, "Not Found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//        }

    @PatchMapping("/{userId}/device-token")
    public ResponseEntity<?> setDeviceToken(@PathVariable Long userId, @RequestParam String deviceToken) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setDeviceToken(deviceToken);
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse(200,"Device token updated successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/checkPhoneNull")
//    public Boolean checkUserPhoneNullByEmail(@RequestParam String email) {
//        User user = userRepository.findByEmail(email);
//        if ( user != null && user.getPhone() != null ) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404,"User Not Found")); // User not found or phone number is null
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
        }

        User userOauth = new User();
        userOauth.setUsername(username);
        userOauth.setEmail(email);
        userOauth.setPhone(phone);
        userOauth.setCity(city);
        userOauth.setPassword(encoder.encode("defaultPassword"));
        userOauth.setRoles(Collections.singleton(userRole));

        userRepository.save(userOauth);

        return ResponseEntity.ok(new ApiResponse(200, "User Created Successfully"));
    }


    }