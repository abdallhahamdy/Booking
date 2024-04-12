package com.AlTaraf.Booking.Controller;

import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequest;
import com.AlTaraf.Booking.Dto.Notifications.PushNotificationRequestForAll;
import com.AlTaraf.Booking.Dto.Notifications.Response.PushNotificationResponse;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Mapper.Notification.NotificationForAllMapper;
import com.AlTaraf.Booking.Mapper.Notification.NotificationMapper;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Repository.NotificationRepository;
import com.AlTaraf.Booking.Repository.role.RoleRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.notification.NotificationService;
import com.AlTaraf.Booking.Entity.Notifications.Notifications;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Notification")
public class NotificationController {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationForAllMapper notificationForAllMapper;

    @Autowired
    RoleRepository roleRepository;


    @PostMapping("/Send")
    public ResponseEntity<?> sendPushNotification(@RequestBody PushNotificationRequest request) {
        try {
            Notifications notification = notificationMapper.dtoToEntity(request);
            notificationRepository.save(notification);

            notificationService.sendPushMessage(request.getTitle(), request.getBody(), request.getUserId());

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-Guest-One-User")
    public ResponseEntity<?> sendPushNotificationforGuest(@RequestBody PushNotificationRequest request) {
        try {
            Notifications notification = notificationMapper.dtoToEntity(request);
            Role role = roleRepository.findById(1L).orElse(null);

            notification.setRole(role);
            notificationRepository.save(notification);

            User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_GUEST,request.getUserId());
            if (user != null ) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to user with role Guest!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-Lessor-One-User")
    public ResponseEntity<?> sendPushNotificationforLessor(@RequestBody PushNotificationRequest request) {
        try {
            Notifications notification = notificationMapper.dtoToEntity(request);
            Role role = roleRepository.findById(2L).orElse(null);

            notification.setRole(role);
            notificationRepository.save(notification);

            User user = userRepository.findByRolesNameAndUserId(ERole.ROLE_LESSOR,request.getUserId());
            if (user != null ) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to user with role Lessor!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-All")
    public ResponseEntity<?> sendPushNotificationToAll(@RequestBody PushNotificationRequestForAll request) {
        try {
            List<User> users = userRepository.findAll();

            for (User user : users) {
                Notifications notification = notificationForAllMapper.dtoToEntity(request);
                notification.setUser(user);
                notificationRepository.save(notification);

                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notifications sent successfully to all users!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-All-Guest")
    public ResponseEntity<?> sendPushNotificationforAllGuest(@RequestBody PushNotificationRequestForAll request) {
        try {
            Notifications notification = notificationForAllMapper.dtoToEntity(request);

            Role role = roleRepository.findById(1L).orElse(null);

            notification.setRole(role);

            notificationRepository.save(notification);

            List<User> users = userRepository.findByRolesName(ERole.ROLE_GUEST);
            for (User user : users) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to all users with role Guests!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

    @PostMapping("/Send-For-All-Lessor")
    public ResponseEntity<?> sendPushNotificationforLessor(@RequestBody PushNotificationRequestForAll request) {
        try {
            Notifications notification = notificationForAllMapper.dtoToEntity(request);

            Role role = roleRepository.findById(2L).orElse(null);

            notification.setRole(role); // Set role ID to 2
            notificationRepository.save(notification);

            List<User> users = userRepository.findByRolesName(ERole.ROLE_LESSOR);
            for (User user : users) {
                notificationService.sendPushMessage(request.getTitle(), request.getBody(), user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200,"Push notification sent successfully to all users with role Lessors!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500,"Failed to send push notification."));
        }
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getNotificationsByUserId(
//            @PathVariable Long userId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Notifications> notifications = notificationService.getNotificationsByUserId(userId, pageable);
//
//        Page<PushNotificationResponse> response = notifications.map(NotificationMapper.INSTANCE::entityToDto);
//
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getNotificationsByUserIdAndRoleName(
            @PathVariable Long userId,
            @RequestParam(required = false) ERole roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Notifications> notifications;

        if (roleName == null) {
            notifications = notificationService.getNotificationsByUserId(userId, pageable);
        } else {
            notifications = notificationService.findAllByUserIdAndRoleName(userId, roleName, pageable);
        }

        Page<PushNotificationResponse> response = notifications.map(NotificationMapper.INSTANCE::entityToDto);

        return ResponseEntity.ok(response);
    }

}