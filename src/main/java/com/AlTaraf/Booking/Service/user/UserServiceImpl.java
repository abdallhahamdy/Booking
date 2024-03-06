package com.AlTaraf.Booking.Service.user;

import com.AlTaraf.Booking.Dto.User.UserRegisterDashboardDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.User.UserRegisterDto;
import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import com.AlTaraf.Booking.Entity.User.UserDashboard;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Entity.enums.ERoleDashboard;
import com.AlTaraf.Booking.Mapper.CityMapper;
import com.AlTaraf.Booking.Mapper.UserMapper;
import com.AlTaraf.Booking.Payload.request.PasswordResetDto;
import com.AlTaraf.Booking.Repository.role.RoleDashboardRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.user.UserDashboardRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Security.jwt.JwtUtils;
import com.AlTaraf.Booking.Service.cityAndRegion.CityService;
import com.AlTaraf.Booking.Service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    CityService cityService;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleDashboardRepository roleDashboardRepository;

    @Autowired
    private UserDashboardRepository userDashboardRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    public String generateOtpForUser() {
        // For simplicity, let's assume a random 4-digit OTP
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    @Override
    public Boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            // Handle empty email case, e.g., return false or throw an exception
            return false;
        }

        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String email, String phone, Set<ERole> roleNames) {
        return userRepository.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleNames);
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {
        String phone = userRegisterDto.getPhoneNumber();
        Optional<User> existingUserOptional = userRepository.findByPhone(phone);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // User with the same phone number exists, update roles if necessary
            Set<String> strRoles = userRegisterDto.getRoles();
            Set<Role> newRoles = new HashSet<>();

            // Convert role names to Role entities
            if (strRoles != null) {
                strRoles.forEach(roleName -> {
                    Role role = roleRepository.findByName(ERole.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(role);
                });
            }

            // Check if there are new roles to add
            boolean rolesChanged = false;
            for (Role newRole : newRoles) {
                if (!existingUser.getRoles().contains(newRole)) {
                    existingUser.getRoles().add(newRole);
                    rolesChanged = true;
                }
            }

            // If roles are updated, save the user entity
            if (rolesChanged) {
                return userRepository.save(existingUser);
            } else {
                // If roles are not changed, return the existing user
                return existingUser;
            }
        } else {
            // User does not exist, proceed with registration
            // Your existing registration code goes here
            Set<String> strRoles = userRegisterDto.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "lessor":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_LESSOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case "guest":
                            Role modRole = roleRepository.findByName(ERole.ROLE_GUEST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            // Check if the city exists
            CityDto cityDto = userRegisterDto.getCity();
            City city = cityMapper.cityDTOToCity(cityDto);
            if (city == null) {
                throw new RuntimeException("City " + cityDto.getCityName() + " not found");
            }

            // Map UserRegisterDto to User entity
            User user = new User();
            user.setUsername(userRegisterDto.getName());
            user.setEmail(userRegisterDto.getEmail());
            user.setPassword(encoder.encode(userRegisterDto.getPassword()));
            user.setPhone(phone); // Use the provided phone number
            user.setCity(city);
            user.setRoles(roles);

            // Save the user entity
            return userRepository.save(user);
        }
    }


    @Override
    public UserDashboard registerUserForDashboard(UserRegisterDashboardDto userRegisterDashboardDto) {
        String email = userRegisterDashboardDto.getEmail();
        Optional<UserDashboard> existingUserDashboardOptional = userDashboardRepository.findByEmail(email);

        if (existingUserDashboardOptional.isPresent()) {
            UserDashboard existingUser = existingUserDashboardOptional.get();
            // User with the same phone number exists, update roles if necessary
            Set<String> strRoles = userRegisterDashboardDto.getRoles();
            Set<RoleDashboard> newRoles = new HashSet<>();

            // Convert role names to Role entities
            if (strRoles != null) {
                strRoles.forEach(roleName -> {
                    RoleDashboard role = roleDashboardRepository.findByName(ERoleDashboard.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    newRoles.add(role);
                });
            }

            // Check if there are new roles to add
            boolean rolesChanged = false;
            for (RoleDashboard newRole : newRoles) {
                if (!existingUser.getRoles().contains(newRole)) {
                    existingUser.getRoles().add(newRole);
                    rolesChanged = true;
                }
            }

            // If roles are updated, save the user entity
            if (rolesChanged) {
                return userDashboardRepository.save(existingUser);
            } else {
                // If roles are not changed, return the existing user
                return existingUser;
            }
        } else {
            // User does not exist, proceed with registration
            // Your existing registration code goes here
            Set<String> strRoles = userRegisterDashboardDto.getRoles();
            Set<RoleDashboard> roles = new HashSet<>();

            if (strRoles == null) {
                RoleDashboard userRole = roleDashboardRepository.findByName(ERoleDashboard.ROLE_CUSTOMER_SERVICE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            RoleDashboard adminRole = roleDashboardRepository.findByName(ERoleDashboard.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case "service":
                            RoleDashboard modRole = roleDashboardRepository.findByName(ERoleDashboard.ROLE_CUSTOMER_SERVICE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        default:
                            RoleDashboard userRole = roleDashboardRepository.findByName(ERoleDashboard.ROLE_CUSTOMER_SERVICE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            UserDashboard userDashboard = new UserDashboard();
            userDashboard.setUsername(userRegisterDashboardDto.getName());
            userDashboard.setEmail(email);
            userDashboard.setPassword(encoder.encode(userRegisterDashboardDto.getPassword()));
            userDashboard.setPhone(userRegisterDashboardDto.getPhoneNumber()); // Use the provided phone number
            userDashboard.setRoles(roles);

            // Save the user entity
            return userDashboardRepository.save(userDashboard);
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void resetPasswordByPhone(String phone, PasswordResetDto passwordResetDto) {
        // Retrieve the user from the database using phone number
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the password
        user.setPassword(encoder.encode(passwordResetDto.getNewPassword()));

        // Save the updated user
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // You might want to perform additional validation or business logic here
        userRepository.save(user);
    }

//    @Override
//    public User updateUser(Long id, UserRegisterDto userRegisterDto) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
//
//        // Update user properties based on the UserRegisterDto
//        existingUser.setUsername(userRegisterDto.getName());
//        existingUser.setEmail(userRegisterDto.getEmail());
//        existingUser.setPassword(userRegisterDto.getPassword());
//        existingUser.setPhone(userRegisterDto.getPhoneNumber());
//
//        // Check if the city exists
//        CityDto cityDto = userRegisterDto.getCity();
//        City city = cityMapper.cityDTOToCity(cityDto);  // Use CityMapper to convert CityDto to City
//        if (city == null) {
//            throw new RuntimeException("City " + cityDto.getCityName() + " not found");
//        }
//        existingUser.setCity(city);
//
//        // Check if the roles exist
//        Set<RoleDto> roleDtos = userRegisterDto.getRoles();
//        Set<Role> roles = new HashSet<>();
//        for (RoleDto roleDto : roleDtos) {
//            Role role = roleService.getRoleByName(roleDto.getRoleNameDto());
//            if (role == null) {
//                throw new RuntimeException("Role " + roleDto.getRoleNameDto() + " not found");
//            }
//            roles.add(role);
//        }
//        existingUser.setRoles(roles);
//
//        // Save the updated user entity
//        return userRepository.save(existingUser);
//    }

//    @Override
//    public UserRegisterDto getUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
//
//        return userMapper.userToUserRegisterDto(user);
//    }

//    @Override
//    public List<UserRegisterDto> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return userMapper.userListToUserRegisterDtoList(users);
//    }

//    @Override
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }

    // Example method to generate OTP (replace with your implementation)

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
