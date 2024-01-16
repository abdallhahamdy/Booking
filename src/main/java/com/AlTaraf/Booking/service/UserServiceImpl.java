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
import com.AlTaraf.Booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CityService cityService;
    private final CityMapper cityMapper;
    private final UserMapper userMapper;
    // Constructor
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, CityService cityService, CityMapper cityMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.cityService = cityService;
        this.cityMapper  = cityMapper;
        this.userMapper = userMapper;
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {

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

        // Check if the city exists
        CityDto cityDto = userRegisterDto.getCity();
        City city = cityMapper.cityDTOToCity(cityDto);  // Use CityMapper to convert CityDto to City
        if (city == null) {
            throw new RuntimeException("City " + cityDto.getCityName() + " not found");
        }

        // Check if a user with the same email or phone number and the same role already exists using custom SQL query
        if (userRepository.existsByEmailAndRolesOrPhoneNumberAndRoles(
                userRegisterDto.getEmail(),
                userRegisterDto.getPhoneNumber(),
                userRegisterDto.getRoles().stream()
                        .map(RoleDto::getRoleName)
                        .collect(Collectors.toList()))) {
            throw new DuplicateUserException("User with the same email, phone number, and role already exists.");
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

    @Override
    public User updateUser(Long id, UserRegisterDto userRegisterDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        // Update user properties based on the UserRegisterDto
        existingUser.setName(userRegisterDto.getName());
        existingUser.setEmail(userRegisterDto.getEmail());
        existingUser.setPassword(userRegisterDto.getPassword());
        existingUser.setPhone(userRegisterDto.getPhoneNumber());

        // Check if the city exists
        CityDto cityDto = userRegisterDto.getCity();
        City city = cityMapper.cityDTOToCity(cityDto);  // Use CityMapper to convert CityDto to City
        if (city == null) {
            throw new RuntimeException("City " + cityDto.getCityName() + " not found");
        }
        existingUser.setCity(city);

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
        existingUser.setRoles(roles);

        // Save the updated user entity
        return userRepository.save(existingUser);
    }

    @Override
    public UserRegisterDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        return userMapper.userToUserRegisterDto(user);
    }

    @Override
    public List<UserRegisterDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.userListToUserRegisterDtoList(users);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
