package com.AlTaraf.Booking.Service.Payment;

import com.AlTaraf.Booking.Dto.TransactionResponseDTO;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    ResponseEntity<?> sendTransactionRequest(Long userId, String customRef, String apiShopToken, UserRepository userRepository, String apiShopTransaction);
}
