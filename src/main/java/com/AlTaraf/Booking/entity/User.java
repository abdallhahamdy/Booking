package com.AlTaraf.Booking.entity;

import com.AlTaraf.Booking.entity.common.JPAEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name="users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    @Size(max = 20)
    private String username;

    @Column(nullable = false)
    @Size(max = 50)
    @Email
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Size(max = 120)
    private String password;

    @ManyToOne
    @JoinColumn(name = "city_id")  // Many users can have the same city
    private City city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
