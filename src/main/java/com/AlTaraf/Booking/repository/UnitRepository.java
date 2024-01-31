package com.AlTaraf.Booking.repository;

import com.AlTaraf.Booking.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    // You can add custom query methods if needed
//    <S extends Unit> List<S> saveAll(Iterable<S> entities);
}