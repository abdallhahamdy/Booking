package com.AlTaraf.Booking.Entity.Role;

import com.AlTaraf.Booking.Entity.enums.ERole;
import com.AlTaraf.Booking.Entity.enums.ERoleDashboard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles_dashboard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_dashboard_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role", nullable = false)
    private ERoleDashboard name;

    private String arabicName;
}