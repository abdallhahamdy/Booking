package com.AlTaraf.Booking.mapper.User;

import com.AlTaraf.Booking.dto.User.UserEditDto;
import com.AlTaraf.Booking.entity.User.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEditDtoMapper {

    UserEditDtoMapper INSTANCE = Mappers.getMapper(UserEditDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "password", target = "password")
    User userEditDtoToUser(UserEditDto userEditDto);

    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "city.id", target = "cityId")
    UserEditDto userToUserEditDto(User user);
}