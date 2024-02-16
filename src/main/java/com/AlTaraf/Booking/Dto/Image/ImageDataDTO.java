package com.AlTaraf.Booking.Dto.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDataDTO {
    private Long id;
    private String name;
    private String imagePath;
}
