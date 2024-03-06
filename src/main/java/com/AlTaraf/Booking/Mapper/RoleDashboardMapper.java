package com.AlTaraf.Booking.Mapper;

import com.AlTaraf.Booking.Dto.Roles.RoleDashboardDto;
import com.AlTaraf.Booking.Dto.Roles.RoleDto;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleDashboardMapper {

    RoleDashboardMapper INSTANCE = Mappers.getMapper(RoleDashboardMapper.class);

    @Mapping(source = "name", target = "roleName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    RoleDashboardDto roleToRoleDto(RoleDashboard role);

    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    RoleDashboard roleDtoToRole(RoleDashboardDto roleDto);

    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    List<RoleDashboardDto> rolesToRoleDtos(List<RoleDashboard> roles); // Add this method


}