package com.AlTaraf.Booking.Entity.Calender;

import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DATE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATE_INFO_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_ID")
    @JsonBackReference
    private ReserveDate reserveDate;
}
