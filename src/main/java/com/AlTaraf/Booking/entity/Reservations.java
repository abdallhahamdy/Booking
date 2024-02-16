package com.AlTaraf.Booking.entity;

import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.common.Auditable;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Reservation")
@Setter
@Getter
@AllArgsConstructor
public class Reservations extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Column(name = "CLIENT_PHONE")
    private String clientPhone;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_room_available",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID")
    )
    private Set<RoomAvailable> roomAvailableSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_available_area",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "AVAILABLE_AREA_ID"))
    private Set<AvailableArea> availableAreaSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_basic_features",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_sub_features",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_food_options",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "food_option_id"))
    private Set<FoodOption> foodOptionsSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    private int capacityHalls;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_periods_unit_halls",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "available_periods_id"))
    private Set<AvailablePeriods> availablePeriodsHallsSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_Room_Details",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_DETAILS_ID"))
    private Set<RoomDetails> roomDetailsSet = new HashSet<>();

    public Reservations() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }
}
