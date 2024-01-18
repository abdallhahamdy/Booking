package com.AlTaraf.Booking.payload.response;

import com.AlTaraf.Booking.dto.RoleDto;
import com.AlTaraf.Booking.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String phone;
    private City city;
    private List<RoleDto> roles;

    public JwtResponse(String jwt,
                       Long id,
                       String username,
                       String email,
                       String phone,
                       City city,
                       List<String> roles) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.city = city;
        // Assuming RoleDto has a constructor that accepts three parameters (id, roleName, arabicRoleName)
//        this.roles = roles.stream().map(role -> new RoleDto(role)).collect(Collectors.toList());

    }
}