package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.entity.Role;
import com.AlTaraf.Booking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    // Constructor
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName).orElse(null);
    }
}
