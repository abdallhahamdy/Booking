package com.AlTaraf.Booking.Controller.Date;

import com.AlTaraf.Booking.Entity.DateCalender.DateInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/Date-For-Calender")
@RestController
public class DateController {

    @GetMapping("/Calender")
    public List<DateInfo> getNext30Days() {
        List<DateInfo> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            LocalDate date = currentDate.plusDays(i);
            dates.add(new DateInfo(i + 1L, date.toString(), isAvailable(date)));
        }

        return dates;
    }

    private boolean isAvailable(LocalDate date) {
        // Add your logic here to determine if the date is available
        return true; // For demonstration purposes, always return true
    }


}