package com.AlTaraf.Booking.Entity.Calender;


import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    @Column(name = "RESERVE_DATE_ID")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "RESERVE_DATE_LIST", joinColumns = @JoinColumn(name = "RESERVER_DATE_ID"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(name = "DATE")
    private List<Date> dates;

    @ManyToOne
    @JoinColumn(name = "ROOM_DETAILS_FOR_AVAILABLE_AREA_ID")
    private RoomDetailsForAvailableArea roomDetailsForAvailableArea;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

//    @Column(name = "RESERVE")
//    private Boolean reserve;

}
