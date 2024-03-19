package com.AlTaraf.Booking.Entity.TechnicalSupport;

import com.AlTaraf.Booking.Entity.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "technical_support")
@Entity
public class TechnicalSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Email
    private String email;

    @Column
    private String message;

    @Column
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;


}
