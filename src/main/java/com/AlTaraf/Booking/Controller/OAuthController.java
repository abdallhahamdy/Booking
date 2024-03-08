package com.AlTaraf.Booking.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/OAuth")
public class OAuthController {

    @GetMapping("/login/oauth2/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }

}
