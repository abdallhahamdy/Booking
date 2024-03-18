package com.AlTaraf.Booking.Payload.response.OauthResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthResponse {
    private boolean message;
    private String token;
    private Long userId;
}
