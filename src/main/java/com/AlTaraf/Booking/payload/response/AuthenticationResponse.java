package com.AlTaraf.Booking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private int statusCode;
    private String message;
    private String otp;

}
