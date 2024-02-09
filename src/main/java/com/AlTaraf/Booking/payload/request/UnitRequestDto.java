package com.AlTaraf.Booking.payload.request;

import com.AlTaraf.Booking.dto.RoomTypeDetailsDTO;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomTypeDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequestDto {
    private Long id;
    private Long unitTypeId;
    private Long userId;
    private String nameUnit;
    private String description;
    private Long cityId;
    private Long regionId;
    private Long accommodationTypeId;

    private Long hotelClassificationId;
//    private List<RoomTypeDetailsDTO> roomTypeDetails;

        private Set<Long> roomAvailableIds;
    private Set<Long> basicFeaturesIds;
    private Set<Long> subFeaturesIds;
    private Set<Long> foodOptionsIds;
    private int adultsAllowed;
    private int childrenAllowed;

    private int capacityHalls;
    private Set<Long> featuresHallsIds;
    private Set<Long> availablePeriodsHallsIds;
    private int oldPriceHall;
    private int newPriceHall;

    private Double latForMapping;
    private Double longForMapping;

}
