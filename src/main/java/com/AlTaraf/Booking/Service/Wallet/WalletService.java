package com.AlTaraf.Booking.Service.Wallet;

import com.AlTaraf.Booking.Entity.Wallet.Wallet;

import java.util.List;

public interface WalletService {

    List<Wallet> getWalletsByUserId(Long userId);
}
