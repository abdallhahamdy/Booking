package com.AlTaraf.Booking.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckApiResponse {
    private int statusCode;
    private String message;
    private Boolean check;
}
