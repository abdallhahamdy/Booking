package com.AlTaraf.Booking.repository.cityAndregion;

import com.AlTaraf.Booking.entity.cityAndregion.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByCityId(Long cityId);
    Optional<Region> findByIdAndCityId(Long regionId, Long cityId);

}
