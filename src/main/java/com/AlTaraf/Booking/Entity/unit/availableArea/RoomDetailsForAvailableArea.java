package com.AlTaraf.Booking.Entity.unit.availableArea;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "room_details_available_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsForAvailableArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_DETAILS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AVAILABLE_AREA_ID")
    private AvailableArea availableArea;

    @Column(name = "ROOM_NUMBER")
    private int roomNumber;

   @Column(name = "NEW_PRICE")
   private int newPrice;

   @Column(name = "OLD_PRICE")
   private int oldPrice;

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonBackReference
    private Unit unit;

}
