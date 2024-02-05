package com.AlTaraf.Booking.entity.TechnicalSupport;

import com.AlTaraf.Booking.entity.User.User;
import jakarta.persistence.*;
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
    @jakarta.validation.constraints.Email
    private String email;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;
}
