package com.AlTaraf.Booking.payload.response;

import com.AlTaraf.Booking.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<RoleDto> roles;

    public JwtResponse(String jwt,
                       Long id,
                       String username,
                       String email,
                       String phone,
                       List<String> roles) {
    }
}