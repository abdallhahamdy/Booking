package com.AlTaraf.Booking.Entity.Calender;


import com.AlTaraf.Booking.Entity.unit.Unit;
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
public class ReserveDateHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVER_DATE_HOTEL_ID")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "RESERVE_DATE_LIST", joinColumns = @JoinColumn(name = "RESERVER_DATE_HOTEL_ID"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(name = "DATE")
    private List<Date> dates;

    @ManyToOne(cascade = CascadeType.ALL)
    private RoomDetails roomDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    private Unit unit;

//    @Column(name = "RESERVE")
//    private Boolean reserve;

}
