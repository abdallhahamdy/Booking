package com.AlTaraf.Booking.Service.Payment;

import com.AlTaraf.Booking.Dto.TransactionResponseDTO;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import com.AlTaraf.Booking.Entity.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PayemntRepository payemntRepository;

    @Override
    public ResponseEntity<?> sendTransactionRequest(Long userId, String customRef, String apiShopToken, UserRepository userRepository, String apiShopTransaction) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiShopToken);
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            headers.set("X-RateLimit-Limit", "30");
            headers.set("X-RateLimit-Remaining", "29");

//            Payment paymentEntity = new Payment();
            Payment payment = payemntRepository.findByCustomRef(customRef);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("store_id", payment.getId());
            body.add("custom_ref", customRef);
            HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);
            System.out.println("user Id: " + userId);

            User user = userRepository.findByUserId(userId);
            payment.setUser(user);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TransactionResponseDTO> response = restTemplate.postForEntity(apiShopTransaction, httpRequest, TransactionResponseDTO.class);

            TransactionResponseDTO transactionResponseDTO = response.getBody();

            if (transactionResponseDTO != null && transactionResponseDTO.getData() != null) {
                Double amount = Double.parseDouble(transactionResponseDTO.getData().getAmount());
                System.out.println("amount: " + amount);
                System.out.println("user id: " + user.getId());

                double currentWalletBalance = user.getWallet();
                currentWalletBalance += amount;
                user.setWallet(currentWalletBalance);
                userRepository.save(user);

                Payment paymentEntityToActive = payemntRepository.findByCustomRef(customRef);

                paymentEntityToActive.setIsActive(true);

                payemntRepository.save(paymentEntityToActive);

                System.out.println("payment: " + paymentEntityToActive.getCustom_ref() + " Payment Active: " + paymentEntityToActive.getIsActive());

                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
//            }
            } else {
                String errorMessage = "Payment_Failed.message";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        } catch (HttpClientErrorException.NotFound e) {
            // Handle 404 Not Found error
            String errorMessage = "Invalid_Custom_Ref.message";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            System.out.println("Error Transaction Payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Or handle the error as needed
        }
    }
}