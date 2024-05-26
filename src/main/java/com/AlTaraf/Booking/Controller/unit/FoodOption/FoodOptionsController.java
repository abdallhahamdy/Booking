package com.AlTaraf.Booking.Controller.unit.FoodOption;

import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.foodOption.FoodOptionService;
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
public class FoodOptionsController {

    @Autowired
    FoodOptionService foodOptionService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-Food-Option")
    public ResponseEntity<?> getAllFoodOption() {
        List<FoodOption> foodOptionList = foodOptionService.getAllFoodOption();

        if (!foodOptionList.isEmpty()) {
            return new ResponseEntity<>(foodOptionList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, messageSource.getMessage("no_content.message", null, LocaleContextHolder.getLocale())));
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
