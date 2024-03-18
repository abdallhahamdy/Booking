package com.AlTaraf.Booking.Payload.response.OauthResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthResponseForSignUp {
    private String message;
    private String token;
    private Long userId;
}
