package com.AlTaraf.Booking.payload.request;

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
}
