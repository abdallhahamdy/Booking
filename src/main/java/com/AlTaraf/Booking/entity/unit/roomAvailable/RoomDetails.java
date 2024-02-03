package com.AlTaraf.Booking.entity.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.Unit;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "room_details")
public class RoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_DETAILS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_AVAILABLE_ID")
    private RoomAvailable roomAvailable;

    @Column(name = "ROOM_NUMBER")
    private int roomNumber;

    @Column(name = "NEW_PRICE")
    private BigDecimal newPrice;

    @Column(name = "OLD_PRICE")
    private BigDecimal oldPrice;

    public RoomDetails() {
    }

    public RoomDetails(Long id, RoomAvailable roomAvailable, int roomNumber, BigDecimal newPrice, BigDecimal oldPrice) {
        this.id = id;
        this.roomAvailable = roomAvailable;
        this.roomNumber = roomNumber;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomAvailable getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(RoomAvailable roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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
