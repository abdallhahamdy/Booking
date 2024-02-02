package com.AlTaraf.Booking.entity.unit.subFeature;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_feature")
public class SubFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUB_FEATURE_NAME")
    private String subFeatureName;

    @Column(name = "SUB_FEATURE_ARABIC_NAME")
    private String subFeatureArabicName;

    public SubFeature() {
    }

    public SubFeature(Long id, String subFeatureName, String subFeatureArabicName) {
        this.id = id;
        this.subFeatureName = subFeatureName;
        this.subFeatureArabicName = subFeatureArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubFeatureName() {
        return subFeatureName;
    }

    public void setSubFeatureName(String subFeatureName) {
        this.subFeatureName = subFeatureName;
    }

    public String getSubFeatureArabicName() {
        return subFeatureArabicName;
    }

    public void setSubFeatureArabicName(String subFeatureArabicName) {
        this.subFeatureArabicName = subFeatureArabicName;
    }
}
