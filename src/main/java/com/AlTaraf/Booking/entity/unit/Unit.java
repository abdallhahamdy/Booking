package com.AlTaraf.Booking.entity.unit;

import com.AlTaraf.Booking.entity.Image.ImageData;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.User.User;
import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.cityAndregion.Region;
import com.AlTaraf.Booking.entity.common.Auditable;
import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.entity.unit.unitType.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "unit")
@Setter
@Getter
@AllArgsConstructor
public class Unit extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UNIT_TYPE_ID", nullable = false)
    private UnitType unitType;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ACCOMMODATION_TYPE_ID", nullable = true)
    private AccommodationType accommodationType;

    @OneToMany(mappedBy = "unit", fetch = FetchType.EAGER)
    private List<ImageData> images;

    @Column(name = "NAME_UNIT")
    private String nameUnit;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CITY_ID" , nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "REGION_ID", nullable = false)
    private Region region;


    @ManyToOne
    @JoinColumn(name = "HOTEL_CLASSIFICATION_ID", nullable = true)
    private HotelClassification hotelClassification;

    // الغرف المتاحة فنادق بداية

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_room_available",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID")
    )
    private Set<RoomAvailable> roomAvailableSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_basic_features",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_sub_features",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_food_options",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "food_option_id"))
    private Set<FoodOption> foodOptionsSet = new HashSet<>();

    // الغرف المتاحة فنادق نهاية

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    @Column(name = "FAVORITE")
    private Boolean favorite;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    // قاعات المناسبات البداية
    private int capacityHalls;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "features_unit_halls",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_halls_id"))
    private Set<FeatureForHalls> featuresHallsSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "available_periods_unit_halls",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "available_periods_id"))
    private Set<AvailablePeriods> availablePeriodsHallsSet = new HashSet<>();

    private int oldPriceHall;

    private int newPriceHall;

    private Double latForMapping;

    private Double longForMapping;

    // قاعات المناسبات النهاية


}
