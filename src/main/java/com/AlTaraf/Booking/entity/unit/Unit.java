package com.AlTaraf.Booking.entity.unit;

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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "unit")
public class Unit extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UNIT_TYPE_ID", nullable = false)
    private UnitType unitType;

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
    @JoinColumn(name = "HOTEL_CLASSIFICATION_ID")
    private HotelClassification hotelClassification;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_room_available",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID")
    )
    private List<RoomAvailable> roomAvailableList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_basic_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeatures = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_sub_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeatures = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_food_options",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "food_option_id"))
    private Set<FoodOption> foodOptions = new HashSet<>();

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
    private Set<FeatureForHalls> featuresHalls = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "available_periods_unit_halls",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "available_periods_id"))
    private Set<AvailablePeriods> availablePeriodsHalls = new HashSet<>();

    private int oldPriceHall;

    private int newPriceHall;

    // قاعات المناسبات النهاية

    public Unit() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }

    public Unit(Long id, UnitType unitType, AccommodationType accommodationType, String nameUnit, String description, City city, Region region, HotelClassification hotelClassification, List<RoomAvailable> roomAvailableList, Set<Feature> basicFeatures, Set<SubFeature> subFeatures, Set<FoodOption> foodOptions, int adultsAllowed, int childrenAllowed, Boolean favorite, StatusUnit statusUnit, int capacityHalls, Set<FeatureForHalls> featuresHalls, Set<AvailablePeriods> availablePeriodsHalls, int oldPriceHall, int newPriceHall) {
        this.id = id;
        this.unitType = unitType;
        this.accommodationType = accommodationType;
        this.nameUnit = nameUnit;
        this.description = description;
        this.city = city;
        this.region = region;
        this.hotelClassification = hotelClassification;
        this.roomAvailableList = roomAvailableList;
        this.basicFeatures = basicFeatures;
        this.subFeatures = subFeatures;
        this.foodOptions = foodOptions;
        this.adultsAllowed = adultsAllowed;
        this.childrenAllowed = childrenAllowed;
        this.favorite = favorite;
        this.statusUnit = statusUnit;
        this.capacityHalls = capacityHalls;
        this.featuresHalls = featuresHalls;
        this.availablePeriodsHalls = availablePeriodsHalls;
        this.oldPriceHall = oldPriceHall;
        this.newPriceHall = newPriceHall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public HotelClassification getHotelClassification() {
        return hotelClassification;
    }

    public void setHotelClassification(HotelClassification hotelClassification) {
        this.hotelClassification = hotelClassification;
    }

    public List<RoomAvailable> getRoomAvailableList() {
        return roomAvailableList;
    }

    public void setRoomAvailableList(List<RoomAvailable> roomAvailableList) {
        this.roomAvailableList = roomAvailableList;
    }

    public int getAdultsAllowed() {
        return adultsAllowed;
    }

    public void setAdultsAllowed(int adultsAllowed) {
        this.adultsAllowed = adultsAllowed;
    }

    public Set<Feature> getBasicFeatures() {
        return basicFeatures;
    }

    public void setBasicFeatures(Set<Feature> basicFeatures) {
        this.basicFeatures = basicFeatures;
    }

    public Set<SubFeature> getSubFeatures() {
        return subFeatures;
    }

    public void setSubFeatures(Set<SubFeature> subFeatures) {
        this.subFeatures = subFeatures;
    }

    public Set<FoodOption> getFoodOptions() {
        return foodOptions;
    }

    public void setFoodOptions(Set<FoodOption> foodOptions) {
        this.foodOptions = foodOptions;
    }

    public int getChildrenAllowed() {
        return childrenAllowed;
    }

    public void setChildrenAllowed(int childrenAllowed) {
        this.childrenAllowed = childrenAllowed;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public StatusUnit getStatusUnit() {
        return statusUnit;
    }

    public void setStatusUnit(StatusUnit statusUnit) {
        this.statusUnit = statusUnit;
    }

    public int getCapacityHalls() {
        return capacityHalls;
    }

    public void setCapacityHalls(int capacityHalls) {
        this.capacityHalls = capacityHalls;
    }

    public Set<FeatureForHalls> getFeaturesHalls() {
        return featuresHalls;
    }

    public void setFeaturesHalls(Set<FeatureForHalls> featuresHalls) {
        this.featuresHalls = featuresHalls;
    }

    public Set<AvailablePeriods> getAvailablePeriodsHalls() {
        return availablePeriodsHalls;
    }

    public void setAvailablePeriodsHalls(Set<AvailablePeriods> availablePeriodsHalls) {
        this.availablePeriodsHalls = availablePeriodsHalls;
    }

    public int getOldPriceHall() {
        return oldPriceHall;
    }

    public void setOldPriceHall(int oldPriceHall) {
        this.oldPriceHall = oldPriceHall;
    }

    public int getNewPriceHall() {
        return newPriceHall;
    }

    public void setNewPriceHall(int newPriceHall) {
        this.newPriceHall = newPriceHall;
    }
}
