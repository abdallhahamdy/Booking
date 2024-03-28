package com.AlTaraf.Booking.Payload.response.TechnicalSupport;

import com.AlTaraf.Booking.Dto.Image.ImageDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportUnitsResponse {
    private Long id;
    private List<ImageDataDTO> imageDataDTOs;
    private String name;
    private String email;
    private String message;
    private Boolean seen;
    private Long userId;
    private Long unitId;
    private Duration elapsedTime;
}