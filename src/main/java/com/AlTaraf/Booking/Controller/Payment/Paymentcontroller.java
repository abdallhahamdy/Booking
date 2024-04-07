package com.AlTaraf.Booking.Controller.Payment;


import com.AlTaraf.Booking.Entity.Payment;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import com.AlTaraf.Booking.Repository.user.UserRepository;
import com.AlTaraf.Booking.Service.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class Paymentcontroller {

    @Value("${api.shop.url}")
    private String apiShopUrl;

    @Value("${api.shop.token}")
    private String apiShopToken;

    @Value("${api.shop.transaction}")
    private String apiShopTransaction;

    @Autowired
    PayemntRepository payemntRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentService paymentService;

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    @PostMapping("/initiate_payment")
    public ResponseEntity<?> initiatePayment(
            @RequestParam Double amount,
            @RequestParam String phone,
            @RequestParam String email) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiShopToken);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-RateLimit-Limit", "30");
        headers.set("X-RateLimit-Remaining", "29");


        Payment paymentEntity = new Payment();
        paymentEntity.setAmount(amount);
        paymentEntity.setPhone(phone);
        paymentEntity.setEmail(email);
        paymentEntity.setCustom_ref(paymentEntity.getCustom_ref());


        System.out.println("id: " + paymentEntity.getId());
        System.out.println("custom_ref: " + paymentEntity.getCustom_ref());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id", paymentEntity.getId());
        body.add("amount", String.valueOf(amount));
        body.add("phone", phone);
        body.add("email", email);
        body.add("backend_url", paymentEntity.getBackend_url());
        body.add("custom_ref", paymentEntity.getCustom_ref());

        payemntRepository.save(paymentEntity);

        System.out.println("Kos Omk");

        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiShopUrl, httpRequest, String.class);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PostMapping("/Transaction/{userId}")
    public ResponseEntity<?> transaction(
            @PathVariable Long userId,
            @RequestParam String custom_ref
    ) {
        return paymentService.sendTransactionRequest(userId, custom_ref, apiShopToken, userRepository, apiShopTransaction);
    }

}