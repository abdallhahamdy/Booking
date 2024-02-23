package com.AlTaraf.Booking.Payload.response.Reservation;

import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseGetId {
    private Long reservationId;
    private String clientName;
    private String clientPhone;
    private RoomAvailable roomAvailable;
    private AvailableArea availableArea;
    private Set<Feature> basicFeatures;
    private Set<SubFeature> subFeatures;
    private Set<FoodOption> foodOptions;
    private int capacityHalls;
    private Set<AvailablePeriods> availablePeriodsHalls;
    private int adultsAllowed;
    private int childrenAllowed;
    private int price;
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
}
