package com.AlTaraf.Booking.Entity;

import com.AlTaraf.Booking.Payload.request.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.UUID;

@Entity
@Table(name = "PAYMENT")
@Data
@AllArgsConstructor
public class Payment {

    @Id
    @NotNull
    @Column(name = "Custom_Ref")
    private String custom_ref;

    @Column(name = "ID")
    private String id;

    @Column(name = "Amount")
    @NotNull
    private float amount;

    @Column(name = "Phone")
    @NotNull
    private String phone;

    @Column(name = "Email")
    @NotNull
    private String email;

    @Column(name = "Backend_Url")
    @NotNull
    private String backend_url;

    private PaymentMethod payment_method;

    private String our_ref;

    public Payment() {
        this.id = "LE4B3xwrXBNWDEGL5PYVAKbmQgrz6xvjGNZjed7y2M0JaRko9nwl14O3qbQ2n6zN";
        this.custom_ref = UUID.randomUUID().toString();
        this.our_ref = "El Taraf";
        this.backend_url = "/response-payment";
    }

}
