package com.AlTaraf.Booking.Entity.Calender.Hotel;


import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RESERVE_HOTEL_DATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVE_HOTEL_DATE_ID")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVE_HOTEL_DATE_ID", referencedColumnName = "RESERVE_HOTEL_DATE_ID")
    @JsonManagedReference
    private List<DateInfoHotel> datesInfoHotels;

    @ManyToOne
    @JoinColumn(name = "ROOM_DETAILS_ID")
    private RoomDetails roomDetails;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

//    @Column(name = "RESERVE")
//    private Boolean reserve;

}
