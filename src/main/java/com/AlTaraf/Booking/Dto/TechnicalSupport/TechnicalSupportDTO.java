package com.AlTaraf.Booking.Dto.TechnicalSupport;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSupportDTO {
    private Long id;
    private String name;
    private String email;
    private String message;
    private Long userId;
}