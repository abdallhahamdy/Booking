package com.AlTaraf.Booking.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private CityDto city;
    private Set<RoleDto> roles = new HashSet<>();

}
