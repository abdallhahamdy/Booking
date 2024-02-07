//package com.AlTaraf.Booking.controller.UnAuthorize;
//
//import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
//import com.AlTaraf.Booking.dto.User.UserRegisterDto;
//import com.AlTaraf.Booking.entity.enums.ERole;
//import com.AlTaraf.Booking.entity.unit.Unit;
//import com.AlTaraf.Booking.payload.request.LoginRequest;
//import com.AlTaraf.Booking.payload.request.PasswordResetDto;
//import com.AlTaraf.Booking.payload.response.ApiResponse;
//import com.AlTaraf.Booking.payload.response.AuthenticationResponse;
//import com.AlTaraf.Booking.payload.response.CheckApiResponse;
//import com.AlTaraf.Booking.payload.response.JwtResponse;
//import com.AlTaraf.Booking.security.jwt.JwtUtils;
//import com.AlTaraf.Booking.security.service.UserDetailsImpl;
//import com.AlTaraf.Booking.service.unit.UnitService;
//import com.AlTaraf.Booking.service.user.UserService;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/unAuthorize")
//public class UnAuthorizeController {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Autowired
//    UnitService unitService;
//
//    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizeController.class);
//
//    @PostMapping("/Send-OTP")
//    public ResponseEntity<?> sendOTP() {
//
//        // Generate and send OTP (you need to implement this logic)
//        String otp = userService.generateOtpForUser();
//        if (otp != null ) {
//            AuthenticationResponse response = new AuthenticationResponse(200, "OTP Sent successfully!", otp);
//            return ResponseEntity.ok(response);
//        } else {
//            ApiResponse response = new ApiResponse(404, "Not Found!");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }
//
//    @GetMapping("/check-availability")
//    public ResponseEntity<?> checkAvailability(@RequestParam(value = "email", required = false) String email,
//                                               @RequestParam(value = "phone") String phone,
//                                               @RequestParam(value = "roleNames") Set<String> roleNames) {
//
//        Set<ERole> roleNamesSet = roleNames.stream().map(ERole::valueOf).collect(Collectors.toSet());
//
//        boolean existsByEmailAndRolesOrPhoneNumberAndRoles = userService.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleNamesSet);
//        boolean isEmailAvailable = userService.existsByEmail(email);
//        boolean isPhoneAvailable = userService.existsByPhone(phone);
//
//        if (email == null || !email.isEmpty()) {
//            if (existsByEmailAndRolesOrPhoneNumberAndRoles) {
//                CheckApiResponse response = new CheckApiResponse(204, "User with the same email, phone number, and role already exists.", false);
//
//                return ResponseEntity.status(HttpStatus.CONFLICT)
//                        .body(response);
//            }
//        } else if (isPhoneAvailable){
//
//            CheckApiResponse response = new CheckApiResponse(204, "Phone is already taken.", false);
//
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(response);
//        } else if (isEmailAvailable){
//            CheckApiResponse response = new CheckApiResponse(204, "Email is already taken.", false);
//
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(response);
//        }
//
//
//        CheckApiResponse response = new CheckApiResponse(200, "", true);
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    @PostMapping("/Register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
//
//        // Perform user registration
//        userService.registerUser(userRegisterDto);
//
//        ApiResponse response = new ApiResponse(200, "User Registered Successfully!");
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//
//        try {
//
//            // Check if the phone number exists in the database
//            if (!userService.existsByPhone(loginRequest.getPhone())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ApiResponse(400, "Phone number not registered"));
//            }
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
////            String jwt = jwtUtils.generateJwtToken(authentication);
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            userDetails.setStayLoggedIn(loginRequest.isStayLoggedIn());
//
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(new JwtResponse(
//                    jwtUtils.generateJwtToken(authentication, loginRequest.isStayLoggedIn()),
//                    userDetails.getId(),
//                    userDetails.getUsername(),
//                    userDetails.getEmail(),
//                    userDetails.getPhone(),
//                    userDetails.getCity(),
//                    roles));
//        } catch (Exception e) {
//            // Log the exception for debugging
//            logger.error("Error during login:", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Error in login Password is not correct"));
//        }
//    }
//
//    @PutMapping("/forget-password/{phone}")
//    public ResponseEntity<?> resetPassword(
//            @PathVariable String phone,
//            @RequestBody PasswordResetDto passwordResetDto) {
//
//        // Perform password reset
//        try {
//            userService.resetPasswordByPhone(phone, passwordResetDto);
//            return ResponseEntity.ok(new ApiResponse(200, "Password reset successfully."));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse(400, "Failed to reset password: " + e.getMessage()));
//        }
//
//    }
//
//    @GetMapping("/last-month")
//    public ResponseEntity<?> getUnitsAddedLastMonth(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "2") int size) {
//
//        Page<UnitDtoFavorite> units = unitService.getUnitsAddedLastMonth(page, size);
//
//        if (!units.isEmpty()) {
//            return new ResponseEntity<>(units, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/get-Units-By-Accommodation-Type")
//    public ResponseEntity<?> getUnitsByAccommodationType(
//            @RequestParam String accommodationTypeName,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size) {
//
//        Page<UnitDtoFavorite> units = unitService.getUnitsByAccommodationTypeName(accommodationTypeName, page, size);
//
//        if (!units.isEmpty()) {
//            return new ResponseEntity<>(units, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content for Units By Accommodation Type!");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//        }
//    }
//
//    @GetMapping("/units-by-user-city")
//    public ResponseEntity<?> getUnitsByUserCity(@RequestParam Long userId) {
//        List<UnitDtoFavorite> units = unitService.getUnitsByUserCity(userId);
//
//        if (!units.isEmpty()) {
//            return new ResponseEntity<>(units, HttpStatus.OK);
//        } else {
//            ApiResponse response = new ApiResponse(204, "No Content for Units By User City!");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//        }
//    }
//
//    @GetMapping("unit/{id}")
//    public Unit getUnitById(@PathVariable Long id) {
//        return unitService.getUnitById(id);
//    }
//
//}
