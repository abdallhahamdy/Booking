package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.Role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "name", target = "roleName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    RoleDto roleToRoleDto(Role role);

    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    Role roleDtoToRole(RoleDto roleDto);

    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    List<RoleDto> rolesToRoleDtos(List<Role> roles); // Add this method


}