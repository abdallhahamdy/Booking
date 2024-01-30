package com.AlTaraf.Booking.dto;

import com.AlTaraf.Booking.entity.enums.ERole;
import jakarta.persistence.*;

public class RoleDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private ERole roleName;

    public RoleDto() {
    }

    public RoleDto(Long id, ERole roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getRoleName() {
        return roleName;
    }

    public void setRoleName(ERole roleName) {
        this.roleName = roleName;
    }
}
