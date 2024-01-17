package com.AlTaraf.Booking.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@MappedSuperclass
@Data
@EqualsAndHashCode(of = {"arabicName", "englishName", "code"}, callSuper = true)
@ToString(of = {"arabicName", "englishName", "enabled", "code"}, callSuper = true)
public class LookupEntity extends JPAEntity {

    private String arabicName;

    private String englishName;

    private String code;

    @Column(name = "is_enabled")
    private Boolean enabled;

}
