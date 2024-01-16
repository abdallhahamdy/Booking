package com.AlTaraf.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String toLocale(String msgCode, Locale locale) {
        return messageSource.getMessage(msgCode, null, locale);
    }
}
