package com.AlTaraf.Booking.Entity.User;

import com.AlTaraf.Booking.Entity.Favorite.UserFavoriteUnit;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.Image.ImageProfile;
import com.AlTaraf.Booking.Entity.Role.Role;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, name = "name")
//    @Size(max = 20)
    private String username;

//    @Size(max = 50)
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

    @Transient
    @Column
    private boolean stayLoggedIn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ImageProfile> imagesProfiles;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<UserFavoriteUnit> favoriteUnits;

    private Double latForMapping;

    private Double longForMapping;

    @Column(name = "BAN")
    private Boolean ban;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    // Array of warnings
    @ElementCollection
    @Column(name = "warning")
    private List<Boolean> warnings = Arrays.asList(false, false, false);

    @Column(name = "WALLET")
    private Double wallet;

    public Double getWallet() {
        return wallet != null ? wallet : 0.0;
    }

    public User(Long id) {
        this.id = id;
    }

//    public User() {
//        if (wallet == null) {
//            wallet = 0.0;
//        }
//    }
}
