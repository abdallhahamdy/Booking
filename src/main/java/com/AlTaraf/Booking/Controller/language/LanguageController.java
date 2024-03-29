package com.AlTaraf.Booking.Controller.language;

import com.AlTaraf.Booking.i18n.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/Language")
public class LanguageController {

    @Autowired
    I18nUtil i18nUtil;

    @GetMapping(value = "/{name}")
    public String hello(@PathVariable(name = "name") String name) {
        return i18nUtil.getMessage("hello.message", name);

    }

}
