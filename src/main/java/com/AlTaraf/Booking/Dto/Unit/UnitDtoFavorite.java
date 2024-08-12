package com.AlTaraf.Booking.Dto.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class UnitDtoFavorite {

    private Long unitId;
    private Long unitTypeId;
    private String imagePath; // Single image file path
    private String videoPath; // Single image file path
    private String nameUnit;
    private String cityName;
    private String regionName;
    private String arabicCityName;
    private String regionArabicName;
    private Double latForMapping;
    private Double longForMapping;
    private Boolean favorite;
    private int price;
    private String evaluationName;
    private String evaluationArabicName;
    private Integer totalEvaluation;
}