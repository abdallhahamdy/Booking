package com.AlTaraf.Booking.payload.response;


// Example class for the OTP response
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