package com.AlTaraf.Booking.Dto.User;


import com.AlTaraf.Booking.Dto.Image.ImageDataProfileDTO;
import com.AlTaraf.Booking.Entity.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private List<ImageDataProfileDTO> imageDataProfileDTOS;
    private String username;
    private String email;
    private String phone;
    private Long cityId;
    private Date createdDate;
    private Date lastModifiedDate;
    private Set<Role> roles;
    private String deviceToken;
}
