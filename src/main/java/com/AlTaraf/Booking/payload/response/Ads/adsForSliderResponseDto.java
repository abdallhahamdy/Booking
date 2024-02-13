package com.AlTaraf.Booking.payload.response.Ads;

import com.AlTaraf.Booking.dto.Unit.UnitDtoFavorite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adsForSliderResponseDto {
    private Long adsId;
    private String imagePath;
    private UnitDtoFavorite unitDtoFavorite;

}
