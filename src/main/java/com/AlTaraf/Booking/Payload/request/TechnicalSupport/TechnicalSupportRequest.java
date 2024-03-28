package com.AlTaraf.Booking.Payload.request.TechnicalSupport;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportRequest {
    private String name;
    private String email;
    private String message;
    private Long userId;
}