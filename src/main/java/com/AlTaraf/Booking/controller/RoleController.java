package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.mapper.RoleMapper;
import com.AlTaraf.Booking.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Create a new role
    @PostMapping("/create")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role createdRole = roleService.createRole(roleDto);
        RoleDto createdRoleDto = RoleMapper.INSTANCE.roleToRoleDto(createdRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoleDto);
    }

    // Update an existing role
    @PutMapping("/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        Role updatedRole = roleService.updateRole(id, roleDto);
        RoleDto updatedRoleDto = RoleMapper.INSTANCE.roleToRoleDto(updatedRole);
        return ResponseEntity.ok(updatedRoleDto);
    }

    // Get a role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        if (role != null) {
            RoleDto roleDto = RoleMapper.INSTANCE.roleToRoleDto(role);
            return ResponseEntity.ok(roleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all roles
    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        if (!roles.isEmpty()) {
            List<RoleDto> roleDtos = RoleMapper.INSTANCE.rolesToRoleDtos(roles);
            return ResponseEntity.ok(roleDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Delete a role by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.deleteRole(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

