package com.AlTaraf.Booking.Entity.User;

import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.Role.RoleDashboard;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.common.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="users_dashboard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDashboard extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, name = "NAME")
    @Size(max = 20)
    private String username;

    @Size(max = 50)
    @Email()
    @Column(nullable = false, name = "EMAIL")
    private String email;

    private String phone;

    @Column(nullable = false)
    @Size(max = 120)
    private String password;

    @Transient
    @Column
    private boolean stayLoggedIn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles_dashboard",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_dashboard_id"))
    private Set<RoleDashboard> roles = new HashSet<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<ImageData> images;

    public UserDashboard(Long id) {
        this.id = id;
    }
}
