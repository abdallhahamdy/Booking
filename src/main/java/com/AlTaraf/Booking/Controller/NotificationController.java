package com.AlTaraf.Booking.Controller;

import com.AlTaraf.Booking.Dto.MessageDTO;
import com.AlTaraf.Booking.Service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    private FcmService fcmService;

    @PostMapping("/notification")
    String  sendToSpecificDevice(
            @RequestBody MessageDTO note,
            @RequestParam String token) throws FirebaseMessagingException {

        return fcmService.sendNotificationToSpecificDevice(note,token);
    }

}