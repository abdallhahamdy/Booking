package com.AlTaraf.Booking.Controller.language;

//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.LocaleResolver;
//
//import java.util.Locale;
//
//@RestController
//@RequestMapping("/Language")
//public class LanguageController {
//
//    @Autowired
//    private LocaleResolver localeResolver;
//
//    @PostMapping("/language/en")
//    public ResponseEntity<Void> switchToEnglish(HttpServletRequest request, HttpServletResponse response) {
//        localeResolver.setLocale(request, response, Locale.ENGLISH);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/language/ar")
//    public ResponseEntity<Void> switchToArabic(HttpServletRequest request, HttpServletResponse response) {
//        localeResolver.setLocale(request, response, Locale.forLanguageTag("ar"));
//        return ResponseEntity.ok().build();
//    }
//
//}
