package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.CityDto;
import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.exception.DuplicateUserException;
import com.AlTaraf.Booking.exception.UserNotFoundException;
import com.AlTaraf.Booking.mapper.CityMapper;
import com.AlTaraf.Booking.mapper.UserMapper;
import com.AlTaraf.Booking.repository.RoleRepository;
import com.AlTaraf.Booking.repository.UserRepository;
import com.AlTaraf.Booking.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    public String generateOtpForUser() {
        // For simplicity, let's assume a random 4-digit OTP
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByEmailAndRolesOrPhoneNumberAndRoles(String email, String phone, Collection<Long> roleIds) {
        return userRepository.existsByEmailAndRolesOrPhoneNumberAndRoles(email, phone, roleIds);
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {

        // Check if the roles exist
        Set<RoleDto> roleDtos = userRegisterDto.getRoles();
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : roleDtos) {
            Role role = roleService.getRoleById(roleDto.getId());
            if (role == null) {
                throw new RuntimeException("Role " + roleDto.getRoleNameDto() + " not found");
            }
            roles.add(role);
        }

        // Check if the city exists
        CityDto cityDto = userRegisterDto.getCity();
        City city = cityMapper.cityDTOToCity(cityDto);  // Use CityMapper to convert CityDto to City
        if (city == null) {
            throw new RuntimeException("City " + cityDto.getCityName() + " not found");
        }


        // Map UserRegisterDto to User entity
        User user = new User();
        user.setUsername(userRegisterDto.getName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(encoder.encode(userRegisterDto.getPassword()));
        user.setPhone(userRegisterDto.getPhoneNumber());
        user.setCity(city);
        user.setRoles(roles);

        // Save the user entity
        return userRepository.save(user);
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
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

}
