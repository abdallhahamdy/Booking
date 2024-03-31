package com.AlTaraf.Booking.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    @JsonProperty("customer_phone")
    private String customerPhone;

    @JsonProperty("customer_email")
    private String customerEmail;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("owner_name")
    private String ownerName;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("shop_logo")
    private String shopLogo;

    @JsonProperty("shop_url")
    private String shopUrl;

    @JsonProperty("owner_city")
    private String ownerCity;

    @JsonProperty("owner_phone")
    private String ownerPhone;

    @JsonProperty("owner_email")
    private String ownerEmail;

    private String amount;
    private String currency;

    @JsonProperty("gateway_name")
    private String gatewayName;

    private String gateway;

    @JsonProperty("gateway_ref")
    private String gatewayRef;

    @JsonProperty("date_time")
    private String dateTime;

    @JsonProperty("order_history")
    private List<String> orderHistory;

    @JsonProperty("notes_to_customer")
    private String notesToCustomer;

    @JsonProperty("notes_to_shop")
    private String notesToShop;

    private String reference;

    // getters and setters
}
