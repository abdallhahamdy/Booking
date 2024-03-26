package com.AlTaraf.Booking.Entity.Notifications;

import com.AlTaraf.Booking.Entity.User.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NOTIFICATIONS")
@Data
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @ManyToOne
    private User user;
    private String logoUrl;

    public Notifications() {
        this.logoUrl = "https://play.min.io/0123-2123-1232-3232-232/logo.jpeg";
    }
}