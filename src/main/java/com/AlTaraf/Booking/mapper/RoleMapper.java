package com.AlTaraf.Booking.mapper;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "role", target = "roleNameDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicRoleName", target = "arabicRoleNameDto")
    RoleDto roleToRoleDto(Role role);

    @Mapping(source = "roleNameDto", target = "role")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicRoleNameDto", target = "arabicRoleName")
    Role roleDtoToRole(RoleDto roleDto);

    @Mapping(source = "roleName", target = "roleNameDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicRoleName", target = "arabicRoleNameDto")
    List<RoleDto> rolesToRoleDtos(List<Role> roles); // Add this method


}