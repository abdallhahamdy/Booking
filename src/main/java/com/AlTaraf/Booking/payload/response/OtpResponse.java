package com.AlTaraf.Booking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// Example class for the OTP response
@Data
@AllArgsConstructor
public class OtpResponse {
    private String otp;

    public OtpResponse() {
    }

    public OtpResponse(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}