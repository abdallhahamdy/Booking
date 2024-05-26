package com.AlTaraf.Booking.Controller.unit.AccommodationType;

import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.AccommodationType.AccommodationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccommodationTypeController {

    @Autowired
    AccommodationTypeService accommodationTypeService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-Accommodation-Type")
    public ResponseEntity<?> getAccommodationType() {
        List<AccommodationType> accommodationTypeList = accommodationTypeService.getAllAccommodationType();

        if (!accommodationTypeList.isEmpty()) {
            return new ResponseEntity<>(accommodationTypeList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
