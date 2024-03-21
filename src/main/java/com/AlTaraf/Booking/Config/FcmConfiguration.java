package com.AlTaraf.Booking.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FcmConfiguration {

    @Bean
    FirebaseMessaging firebaseMessaging()throws IOException {
        GoogleCredentials googleCredentials =GoogleCredentials.fromStream(new ClassPathResource("booking-416504-firebase-adminsdk-l0r8b-9f82f9fe18.json").getInputStream());
        FirebaseOptions firebaseOptions =FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseMessaging.getInstance(app);
    }

}