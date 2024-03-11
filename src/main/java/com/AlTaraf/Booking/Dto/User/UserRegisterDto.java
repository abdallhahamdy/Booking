package com.AlTaraf.Booking.Dto.User;


import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import com.AlTaraf.Booking.Dto.Image.ImageDataProfileDTO;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private CityDto city;
    private Set<String> roles;
//    private List<ImageDataProfileDTO> imageDataProfileDTOS;
}
