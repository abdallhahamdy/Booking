package com.AlTaraf.Booking.Service;

import com.AlTaraf.Booking.Dto.MessageDTO;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FcmService {

    @Autowired
    FirebaseMessaging firebaseMessaging;
    public String sendNotificationToSpecificDevice(MessageDTO note, String token) throws FirebaseMessagingException {
        Notification notification= Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();
        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();
        return firebaseMessaging.send(message);
    }
    public BatchResponse sendNotificationToMultipleDevices(MessageDTO note, List<String> tokens) throws FirebaseMessagingException {

        Notification notification= Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();

        MulticastMessage message = MulticastMessage
                .builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();
        return firebaseMessaging.sendMulticast(message);
    }
    public void subscribeT0Topic(List<String> tokens, String topic) throws FirebaseMessagingException {
        firebaseMessaging.subscribeToTopic(tokens,topic);
    }
    public void unSubscribeT0Topic(List<String> tokens, String topic) throws FirebaseMessagingException {
        firebaseMessaging.subscribeToTopic(tokens,topic);
    }
    public String sendNotificationToTopic(MessageDTO note, String topic) throws  FirebaseMessagingException{
        Notification notification= Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();
        Message message = Message
                .builder()
                .setTopic(topic)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();
        return firebaseMessaging.send(message);
    }
}