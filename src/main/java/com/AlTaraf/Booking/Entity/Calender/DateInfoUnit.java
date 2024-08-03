package com.AlTaraf.Booking.Entity.Calender;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DATE_INFO_UNIT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATE_INFO_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;


    @ManyToOne
    @JoinColumn(name = "RESERVE_DATE_UNIT_ID")
    @JsonBackReference
    private ReserveDateUnit reserveDateUnit;
}
