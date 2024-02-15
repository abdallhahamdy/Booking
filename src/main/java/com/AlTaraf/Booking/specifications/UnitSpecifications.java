package com.AlTaraf.Booking.specifications;

import com.AlTaraf.Booking.entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.entity.unit.Unit;
import com.AlTaraf.Booking.entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.entity.unit.feature.Feature;
import com.AlTaraf.Booking.entity.unit.foodOption.FoodOption;
import com.AlTaraf.Booking.entity.unit.subFeature.SubFeature;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class UnitSpecifications {

    public static Specification<Unit> byUnitTypeId(Long unitTypeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("unitType").get("id"), unitTypeId);
    }

    public static Specification<Unit> byAccommodationTypeId(Long accommodationTypeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accommodationType").get("id"), accommodationTypeId);
    }

    public static Specification<Unit> byCity(Long cityId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city").get("id"), cityId);
    }


    public static Specification<Unit> byRegion(Long regionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("region").get("id"), regionId);
    }

    public static Specification<Unit> byHotelClassificationId(Long hotelClassificationId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("hotelClassification").get("id"), hotelClassificationId);
    }

    public static Specification<Unit> byBasicFeaturesIds(Set<Long> basicFeaturesIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, Feature> featuresJoin = root.join("basicFeaturesSet", JoinType.INNER);
            return featuresJoin.get("id").in(basicFeaturesIds);
        };
    }

    public static Specification<Unit> bySubFeaturesIds(Set<Long> subFeaturesIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, SubFeature> subFeaturesJoin = root.join("subFeaturesSet", JoinType.INNER);
            return subFeaturesJoin.get("id").in(subFeaturesIds);
        };
    }

    public static Specification<Unit> byFoodOptionsIds(Set<Long> foodOptionsIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, FoodOption> foodOptionsJoin = root.join("foodOptionsSet", JoinType.INNER);
            return foodOptionsJoin.get("id").in(foodOptionsIds);
        };
    }

    public static Specification<Unit> byAvailablePeriod(Long availablePeriodId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(availablePeriodId, root.get("availablePeriodsHallsSet").get("id"));
    }

    public static Specification<Unit> byNewPriceHall(int newPriceHall) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("newPriceHall"), newPriceHall);
    }

    public static Specification<Unit> byAdultsAllowed(int adultsAllowed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("roomDetails").get("adultsAllowed"), adultsAllowed);
    }

    public static Specification<Unit> byChildrenAllowed(int childrenAllowed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("roomDetails").get("childrenAllowed"), childrenAllowed);
    }

}