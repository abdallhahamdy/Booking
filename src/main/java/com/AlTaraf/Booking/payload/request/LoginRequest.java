package com.AlTaraf.Booking.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
    private boolean stayLoggedIn;
    private Set<String> roles;
}