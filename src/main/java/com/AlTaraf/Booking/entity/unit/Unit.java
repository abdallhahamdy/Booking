package com.AlTaraf.Booking.entity.unit;

import com.AlTaraf.Booking.entity.cityAndregion.City;
import com.AlTaraf.Booking.entity.cityAndregion.Region;
import com.AlTaraf.Booking.entity.common.Auditable;
import com.AlTaraf.Booking.entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.entity.unit.hotelClassification.HotelClassification;
import com.AlTaraf.Booking.entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.entity.unit.unitType.UnitType;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @JoinColumn(name = "ACCOMMODATION_TYPE_ID", nullable = false)
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
    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;
    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;
    @Column(name = "FAVORITE")
    private Boolean favorite;
    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    public Unit() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }

    public Unit(Long id, UnitType unitType, AccommodationType accommodationType, String nameUnit, String description, City city, Region region, HotelClassification hotelClassification, int adultsAllowed, int childrenAllowed, Boolean favorite, StatusUnit statusUnit) {
        this.id = id;
        this.unitType = unitType;
        this.accommodationType = accommodationType;
        this.nameUnit = nameUnit;
        this.description = description;
        this.city = city;
        this.region = region;
        this.hotelClassification = hotelClassification;
        this.adultsAllowed = adultsAllowed;
        this.childrenAllowed = childrenAllowed;
        this.favorite = favorite;
        this.statusUnit = statusUnit;
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

    public int getAdultsAllowed() {
        return adultsAllowed;
    }

    public void setAdultsAllowed(int adultsAllowed) {
        this.adultsAllowed = adultsAllowed;
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
}
