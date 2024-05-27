package com.AlTaraf.Booking.Repository.Wallet;

import com.AlTaraf.Booking.Entity.Wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId")
    List<Wallet> findByUserId(@Param("userId") Long userId);
}