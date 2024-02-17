package com.AlTaraf.Booking.Payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequestDto {
//    private Long id;
    private Long unitTypeId;
    private Long userId;
    private String nameUnit;
    private String description;
    private Long cityId;
    private Long regionId;
    private Long accommodationTypeId;

    private Long hotelClassificationId;

    private Set<Long> roomAvailableIds;
    private Set<Long> availableAreaIds;

    private Set<Long> basicFeaturesIds;
    private Set<Long> subFeaturesIds;
    private Set<Long> foodOptionsIds;

    private int capacityHalls;
    private Set<Long> featuresHallsIds;
    private Set<Long> availablePeriodsHallsIds;
    private Integer oldPriceHall;
    private Integer newPriceHall;

    private Double latForMapping;
    private Double longForMapping;
//    private Integer price;
}
