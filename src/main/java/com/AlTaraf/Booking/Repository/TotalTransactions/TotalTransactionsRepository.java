package com.AlTaraf.Booking.Repository.TotalTransactions;

import com.AlTaraf.Booking.Entity.TotalTransactions.TotalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalTransactionsRepository extends JpaRepository<TotalTransactions, Long> {

}
