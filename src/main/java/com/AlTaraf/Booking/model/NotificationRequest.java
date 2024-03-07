package com.AlTaraf.Booking.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {
    private String title;
    private String body;
    private String topic;
    private String token;
}