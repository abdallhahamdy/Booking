package com.AlTaraf.Booking.payload.request.Ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRequestDto {
    private Long id;
    private Long unitId;
    private Long packageAdsId;
    private Long statusId;
}
