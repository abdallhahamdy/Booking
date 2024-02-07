package com.AlTaraf.Booking.entity.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.unitType.RoomType;
import jakarta.persistence.*;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTypeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int number;

    private double oldPrice;

    private double newPrice;

    @ManyToOne
    @JoinColumn(name = "unit_id") // Assuming "unit_id" is the foreign key column name
    private Unit unit;




}
