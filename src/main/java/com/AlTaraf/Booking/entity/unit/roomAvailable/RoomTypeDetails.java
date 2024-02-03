package com.AlTaraf.Booking.entity.unit.roomAvailable;

import jakarta.persistence.*;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class RoomTypeDetails {

    @Column(name = "NUMBER_OF_ROOMS")
    private int numberOfRooms;

    @Column(name = "NEW_PRICE")
    private BigDecimal newPrice;

    @Column(name = "OLD_PRICE")
    private BigDecimal oldPrice;

    public RoomTypeDetails() {
    }

    public RoomTypeDetails(int numberOfRooms, BigDecimal newPrice, BigDecimal oldPrice) {
        this.numberOfRooms = numberOfRooms;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
    }


    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }


}
