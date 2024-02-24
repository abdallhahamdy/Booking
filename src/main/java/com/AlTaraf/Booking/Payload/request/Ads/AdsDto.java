package com.AlTaraf.Booking.Payload.request.Ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {
    private Long id;
    private Long unitId;
    private Long unitTypeId;
    private Long userId;
    private Long packageAdsId;
//    private Long statusId;
}
