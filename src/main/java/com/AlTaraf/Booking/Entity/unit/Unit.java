package com.AlTaraf.Booking.Entity.unit;

import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.Image.ImageData;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Entity.common.Auditable;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.Repository.UserFavoriteUnit.UserFavoriteUnitRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.unit.RoomDetails.RoomDetailsService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "unit")
@Getter
@Setter
@AllArgsConstructor
public class Unit extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UNIT_TYPE_ID", nullable = false)
    private UnitType unitType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ACCOMMODATION_TYPE_ID")
    private AccommodationType accommodationType;

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
    // الغرف المتاحة فنادق نهاية

    // الغرف المتاحة لي الشقق الفندقية و الشقق الخارجية و المنتجعات بداية
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_available_area",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "AVAILABLE_AREA_ID"))
    private Set<AvailableArea> availableAreaSet = new HashSet<>();
    // الغرف المتاحة لي الشقق الفندقية و الشقق الخارجية و المنتجعات نهاية

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_basic_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_sub_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_food_options",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "food_option_id"))
    private Set<FoodOption> foodOptionsSet = new HashSet<>();

    // الغرف المتاحة فنادق نهاية

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

    private Integer oldPriceHall;

    private Integer newPriceHall;

    private Integer ChaletNewPrice;

    private Integer ChaletOldPrice;

    private Integer resortOldPrice;

    private Integer resortNewPrice;

    private Integer price;

    private Double latForMapping;

    private Double longForMapping;

    // قاعات المناسبات النهاية

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RoomDetails> roomDetails;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ImageData> images;

    @Column(name = "DATE_OF_ARRIVAL")
    private LocalDate dateOfArrival;

    @Column(name = "DATE_OF_DEPARTURE")
    private LocalDate departureDate;

    @ManyToOne
    @JoinColumn(name = "EVALUATION_ID")
    private Evaluation evaluation;

    @Transient
    private UserFavoriteUnitRepository userFavoriteUnitRepository;

    @Transient
    private UserRepository userRepository;

    @Transient
    private SecurityContext securityContext;

    @Column(name = "ADULTS_ALLOWED")
    private Integer adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private Integer childrenAllowed;

    @Column
    private Integer totalEvaluation; // Total number of evaluations


    public Unit() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
        this.favorite = false;
        this.dateOfArrival = LocalDate.now(); // Set dateOfArrival to the current date
        this.departureDate = LocalDate.now().plusDays(30); // Set departureDate to 30 days after dateOfArrival
//        this.setFavorite(false);
    }

    public void setNewPriceHall(int newPriceHall) {
        if (newPriceHall >= oldPriceHall) {
            throw new IllegalArgumentException("New price must be less than old price.");
        }
        this.newPriceHall = newPriceHall;
    }

    public void setPrice( Integer price ) {

        if (unitType != null && unitType.getId() == 2) {
            if (newPriceHall == 0) {
                price = oldPriceHall;
            } else {
                price = newPriceHall;
            }
        } else if ( unitType.getId() == 1 &&  accommodationType.getId() == 4 ) {
            if (ChaletNewPrice == 0 ) {
                price = ChaletOldPrice;
            } else {
                price = ChaletNewPrice;
            }
        } else if ( unitType.getId() == 1 && accommodationType.getId() == 5) {
            if (resortNewPrice == 0 ) {
                price = resortOldPrice;
            } else {
                price = resortNewPrice;
            }
        } else {
            this.price = price;
        }
    }

    public void calculatePrice() {
        if (unitType != null && unitType.getId() == 2) {
            if (newPriceHall == 0) {
                price = oldPriceHall;
            } else {
                price = newPriceHall;
            }
        }
    }

    private boolean checkFavorite() {
        if (securityContext != null && securityContext.getAuthentication() != null) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                Optional<User> currentUserOptional = userRepository.findByUsername(((UserDetails) principal).getUsername());
                if (currentUserOptional.isPresent()) {
                    User currentUser = currentUserOptional.get();
                    return userFavoriteUnitRepository.existsByUserAndUnit(currentUser, this);
                }
            }
        }
        return false;
    }
}
