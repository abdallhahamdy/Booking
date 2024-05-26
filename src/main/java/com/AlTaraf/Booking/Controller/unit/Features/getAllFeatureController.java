package com.AlTaraf.Booking.Controller.unit.Features;

import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.unit.feature.FeatureService;
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
public class getAllFeatureController {

    @Autowired
    FeatureService featureService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-All-Feature")
    public ResponseEntity<?> getAllFeature() {
        List<Feature> featureList = featureService.getAllFeature();

        if (!featureList.isEmpty()) {
            return new ResponseEntity<>(featureList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
