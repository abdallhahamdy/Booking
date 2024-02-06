package com.AlTaraf.Booking.dto.User;

import lombok.Data;

@Data
public class UserEditDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Long cityId;
}