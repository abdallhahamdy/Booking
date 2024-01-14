package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.entity.User;
import com.AlTaraf.Booking.dto.UserRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "city.cityName", target = "city.city")
    @Mapping(source = "roles", target = "roles")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);

    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "city.city", target = "city.cityName")
    @Mapping(source = "roles", target = "roles")  // Map the whole Set<RoleDto>
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    UserRegisterDto userToUserRegisterDto(User user);

    List<User> userRegisterDtoListToUserList(List<UserRegisterDto> userRegisterDtoList);

    List<UserRegisterDto> userListToUserRegisterDtoList(List<User> userList);

}
