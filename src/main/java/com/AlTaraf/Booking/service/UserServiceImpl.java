package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.entity.City;
import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CityService cityService;

    // Constructor
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, CityService cityService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.cityService = cityService;
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {

        // Implement user registration logic

        // Check if the user already exists
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with the provided email already exists");
        } else if (userRepository.findByPhone(userRegisterDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("User with the provided phone number already exists");
        }

        // Check if the roles exist
        Set<RoleDto> roleDtos = userRegisterDto.getRoles();
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : roleDtos) {
            Role role = roleService.getRoleByName(roleDto.getRoleName());
            if (role == null) {
                throw new RuntimeException("Role " + roleDto.getRoleName() + " not found");
            }
            roles.add(role);
        }

        // Check if the user has the same role twice
        if (roles.size() < roleDtos.size()) {
            throw new RuntimeException("User cannot register with the same role twice");
        }

        // Check if the city exists
        City city = cityService.getCityByName(userRegisterDto.getCity().getCityName());
        if (city == null) {
            throw new RuntimeException("City " + userRegisterDto.getCity().getCityName() + " not found");
        }

        // Map UserRegisterDto to User entity
        User user = new User();
        user.setName(userRegisterDto.getName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        user.setPhone(userRegisterDto.getPhoneNumber());
        user.setCity(city);
        user.setRoles(roles);

        // Save the user entity
        return userRepository.save(user);
    }
}
