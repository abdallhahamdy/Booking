package com.AlTaraf.Booking.Dto.User;


import com.AlTaraf.Booking.Dto.Image.ImageDataProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
}
