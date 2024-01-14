package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "role", target = "roleName")
    RoleDto roleToRoleDto(Role role);

    @Mapping(source = "roleName", target = "role")
    Role roleDtoToRole(RoleDto roleDto);
}