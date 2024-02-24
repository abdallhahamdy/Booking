package com.AlTaraf.Booking.Entity.Calender;


import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RESERVE_DATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVER_DATE_ID")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "RESERVE_DATE_LIST", joinColumns = @JoinColumn(name = "RESERVER_DATE_ID"))
    @Column(name = "DATE")
    private List<Date> dates;

    @ManyToOne
    private RoomDetailsForAvailableArea roomDetailsForAvailableArea;

    @ManyToOne
    private Unit unit;

    @Column(name = "RESERVE")
    private boolean reserve;

}
