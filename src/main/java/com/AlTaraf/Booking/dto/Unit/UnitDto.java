package com.AlTaraf.Booking.dto.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {

    private Long unitId;
    private List<String> images; // List of image file paths
    private String nameUnit;
    private String cityName;
    private String regionName;

    private String arabicCityName;
    private String regionArabicName;


}