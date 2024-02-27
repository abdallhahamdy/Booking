package com.AlTaraf.Booking.Dto.Unit;

import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class UnitDtoFavorite {

    private final UserFavoriteUnitRepository userFavoriteUnitRepository;

    private Long unitId;
    private Long unitTypeId;
    private List<String> imagePaths; // List of image file paths
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


}