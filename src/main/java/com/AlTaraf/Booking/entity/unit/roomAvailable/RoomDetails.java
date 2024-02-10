package com.AlTaraf.Booking.entity.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "room_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_DETAILS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_AVAILABLE_ID")
    private RoomAvailable roomAvailable;

    @Column(name = "ROOM_NUMBER")
    private int roomNumber;

   @Column(name = "NEW_PRICE")
   private BigDecimal newPrice;

   @Column(name = "OLD_PRICE")
   private BigDecimal oldPrice;

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID", nullable = false)
    private Unit unit;

}
