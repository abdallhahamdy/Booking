package com.AlTaraf.Booking.Entity.Calender.Hotel;

import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DATE_INFO_HOTEL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATE_INFO_HOTEL_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "RESERVE_HOTEL_DATE_ID")
    @JsonBackReference
    private ReserveDateHotel reserveDateHotel;
}
