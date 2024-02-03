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

    private boolean stayLoggedIn;

    public LoginRequest() {
    }

    public LoginRequest(String phone, String password, boolean stayLoggedIn) {
        this.phone = phone;
        this.password = password;
        this.stayLoggedIn = stayLoggedIn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}