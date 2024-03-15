package com.AlTaraf.Booking.Payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaymentRequest {
    private String id;
    private float amount;
    private String phone;
    private String email;
    private String backend_url;
    private String custom_ref;
    private PaymentMethod payment_method;

    public PaymentRequest(String id, float amount, String phone, String email, String backend_url, String custom_ref, PaymentMethod payment_method) {
        this.id = id;
        this.amount = amount;
        this.phone = phone;
        this.email = email;
        this.backend_url = backend_url;
        this.custom_ref = custom_ref;
        this.payment_method = payment_method;
    }
}
