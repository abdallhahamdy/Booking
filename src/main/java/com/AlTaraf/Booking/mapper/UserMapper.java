package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.dto.UserRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "city.cityName", target = "city.city")
    @Mapping(source = "roles", target = "roles")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "username")
    @Mapping(source = "email", target = "email")
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "city.city", target = "city.cityName")
    @Mapping(source = "roles", target = "roles")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "username", target = "name")
    @Mapping(source = "email", target = "email")
    UserRegisterDto userToUserRegisterDto(User user);

    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "city.cityName", target = "city.city")
    @Mapping(source = "roles", target = "roles")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "username")
    @Mapping(source = "email", target = "email")
    List<User> userRegisterDtoListToUserList(List<UserRegisterDto> userRegisterDtoList);

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "city.city", target = "city.cityName")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "roleToRoleDtoList")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "username", target = "name")
    @Mapping(source = "email", target = "email")
    List<UserRegisterDto> userListToUserRegisterDtoList(List<User> userList);


//    @Named("roleToRoleDtoList")
//    public static List<RoleDto> roleToRoleDtoList(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> UserMapper.INSTANCE.roleToRoleDto(role))
//                .collect(Collectors.toList());
//    }

    @Named("roleToRoleDtoList")
    public static List<RoleDto> roleToRoleDtoList(Set<Role> roles) {
        return null;
    }
}

