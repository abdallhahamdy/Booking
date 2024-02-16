package com.AlTaraf.Booking.Payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckPhoneNumberAndEmail {
    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    public CheckPhoneNumberAndEmail() {
    }

    public CheckPhoneNumberAndEmail(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
