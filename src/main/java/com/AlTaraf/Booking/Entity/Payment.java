package com.AlTaraf.Booking.Entity;

import com.AlTaraf.Booking.Payload.request.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "PAYMENT")
@Data
@AllArgsConstructor
public class Payment {

    @Id
    private String custom_ref;
    private String id;
    private float amount;
    private String phone;
    private String email;
    private String backend_url;
    private PaymentMethod payment_method;
    private String our_ref;

    public Payment() {
        this.id = "LE4B3xwrXBNWDEGL5PYVAKbmQgrz6xvjGNZjed7y2M0JaRko9nwl14O3qbQ2n6zN";
        this.custom_ref = UUID.randomUUID().toString();
        this.our_ref = "66666";
    }

}
