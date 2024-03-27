package com.AlTaraf.Booking.Payload.response.TechnicalSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportUnitsResponse {
    private Long id;
    private String name;
    private String message;
    private Boolean seen;
    private Long userId;
    private Long unitId;
    private Duration elapsedTime;
}