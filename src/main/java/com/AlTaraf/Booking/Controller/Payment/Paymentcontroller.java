package com.AlTaraf.Booking.Controller.Payment;


import com.AlTaraf.Booking.Dto.payment.PaymentDto;
import com.AlTaraf.Booking.Entity.Payment;
import com.AlTaraf.Booking.Repository.payment.PayemntRepository;
import com.AlTaraf.Booking.Service.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class Paymentcontroller {

    @Autowired
    PayemntRepository payemntRepository;

    @Autowired
    PaymentService paymentService;

    @PostMapping("/initiate_payment")
    public ResponseEntity<?> initiatePayment(
            @RequestParam Double amount,
            @RequestParam String phone,
            @RequestParam String email) {

        return paymentService.initialPayment(amount, phone, email);
    }

    @PostMapping("/Transaction/{userId}")
    public ResponseEntity<?> transaction(
            @PathVariable Long userId,
            @RequestParam String custom_ref
    ) {
        return paymentService.sendTransactionRequest(userId, custom_ref);
    }

    @PostMapping("/back-end-url")
    public ResponseEntity<?> backEndUrl(@RequestBody PaymentDto paymentDto) {
        Payment paymentEntity = payemntRepository.findByCustomRef(paymentDto.getCustom_ref());
        paymentEntity.setPayment_method(paymentDto.getPayment_method());
        payemntRepository.save(paymentEntity);

        return ResponseEntity.ok(paymentDto);
    }


}