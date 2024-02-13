package com.AlTaraf.Booking.dto.User;


import com.AlTaraf.Booking.dto.cityDtoAndRoleDto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private CityDto city;
    private Set<String> roles;
}
