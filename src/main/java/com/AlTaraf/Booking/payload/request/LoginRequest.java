package com.AlTaraf.Booking.payload.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;

@Data
public class LoginRequest {
    @NotBlank
    private String phone;

    @NotBlank
    private String password;

}