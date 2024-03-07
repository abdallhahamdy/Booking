package com.AlTaraf.Booking.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponse {
    private int status;
    private String message;
}