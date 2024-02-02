package com.AlTaraf.Booking.entity.unit.feature;

import jakarta.persistence.*;

@Entity
@Table(name ="feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEATURE_NAME")
    private String featureName;

    @Column(name = "FEATURE_ARABIC_NAME")
    private String featureArabicName;

    public Feature() {
    }

    public Feature(Long id, String featureName, String featureArabicName) {
        this.id = id;
        this.featureName = featureName;
        this.featureArabicName = featureArabicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureArabicName() {
        return featureArabicName;
    }

    public void setFeatureArabicName(String featureArabicName) {
        this.featureArabicName = featureArabicName;
    }
}
