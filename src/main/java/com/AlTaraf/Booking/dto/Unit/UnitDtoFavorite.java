package com.AlTaraf.Booking.dto.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDtoFavorite {

    private Long unitId;
    private List<String> imagePaths; // List of image file paths
    private String nameUnit;
    private String cityName;
    private String regionName;
    private String arabicCityName;
    private String regionArabicName;
    private Double latForMapping;
    private Double longForMapping;
    private Boolean favorite;


}