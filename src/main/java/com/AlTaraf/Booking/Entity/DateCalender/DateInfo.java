package com.AlTaraf.Booking.Entity.DateCalender;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DATE_CALENDER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "DATE")
    private String date;

    @Column(name = "AVAILABLE")
    private boolean available;
}
