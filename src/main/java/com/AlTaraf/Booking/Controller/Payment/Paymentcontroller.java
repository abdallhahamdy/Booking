package com.AlTaraf.Booking.Controller.Payment;

import com.AlTaraf.Booking.Entity.Payment;
import com.AlTaraf.Booking.Payload.request.PaymentMethod;
import com.AlTaraf.Booking.Payload.request.PaymentRequest;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

//@RestController
//@RequestMapping("/payment")
//public class Paymentcontroller {
//
//    @Value("${api.shop.url}")
//    private String apiShopUrl;
//
//    @Value("${api.shop.token}")
//    private String apiShopToken;
//
//    @Autowired
//    PayemntRepository payemntRepository;

//    private String generateId() {
//        return UUID.randomUUID().toString();
//    }

//    @PostMapping("/initiate_payment")
//    public ResponseEntity<?> initiatePayment(
//            @RequestParam float amount,
//            @RequestParam String phone,
//            @RequestParam String email,
//            @RequestParam String backend_url,
//            @RequestParam("payment_method") PaymentMethod payment_method) {

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiShopToken);
//        headers.set("Accept", "application/json");
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//        headers.set("X-RateLimit-Limit", "30");
//        headers.set("X-RateLimit-Remaining", "29");

//        String id = "LE4B3xwrXBNWDEGL5PYVAKbmQgrz6xvjGNZjed7y2M0JaRko9nwl14O3qbQ2n6zN";
//        String custom_ref = generateId();
//        String our_ref = "66666";
//
//        System.out.println("id: " + id);
//        System.out.println("custom_ref: " + custom_ref);
//        System.out.println("our_ref: " + our_ref);

//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("id", id);
//        body.add("amount", String.valueOf(amount));
//        body.add("phone", phone);
//        body.add("email", email);
//        body.add("backend_url", backend_url);
//        body.add("custom_ref", custom_ref);
//        body.add("payment_method", payment_method.toString()); // Assuming payment_method is an enum
//        body.add("our_ref", our_ref);

//        Payment paymentEntity = new Payment();
//        paymentEntity.setAmount(amount);
//        paymentEntity.setPhone(phone);
//        paymentEntity.setEmail(email);
//        paymentEntity.setBackend_url(backend_url);
//
//        // Assuming generateId() generates a unique custom_ref for each payment
//        String custom_ref = generateId();
//        paymentEntity.setCustom_ref(custom_ref);
//
//        // Set other fields as needed
//        paymentEntity.setOur_ref("66666");
//        paymentEntity.setPayment_method(payment_method);
//
//        // Save the payment entity to the database
//        payemntRepository.save(paymentEntity);


//        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(body, headers);

//        RestTemplate restTemplate = new RestTemplate();
////        ResponseEntity<String> response = restTemplate.postForEntity(apiShopUrl, httpRequest, String.class);
//
//        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
//    }
//    @PostMapping("/payment_response")
//    public ResponseEntity<?> handlePaymentResponse(@RequestBody PaymentResponse paymentResponse) {
//        // Handle the payment response according to your requirements
//        // For example, you can log the response or process it further
//
//        // Return the response in the specified format
//        return ResponseEntity.ok().body(paymentResponse);
//    }

//}