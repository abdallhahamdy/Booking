//package com.AlTaraf.Booking.entity.unit.roomAvailable;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "single_room")
//public class SingleRoom implements RoomTypes {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "room_available")
//    private RoomAvailable roomAvailable;
//
//    @Column(name = "NEW_PRICE")
//    private int newPrice;
//
//    @Column(name = "OLD_PRICE")
//    private int oldPrice;
//
//    @Column(name = "NUMBER")
//    private int number;
//
//    public SingleRoom() {
//    }
//
//    public SingleRoom(Long id, RoomAvailable roomAvailable, int newPrice, int oldPrice, int number) {
//        this.id = id;
//        this.roomAvailable = roomAvailable;
//        this.newPrice = newPrice;
//        this.oldPrice = oldPrice;
//        this.number = number;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public RoomAvailable getRoomAvailable() {
//        return roomAvailable;
//    }
//
//    public void setRoomAvailable(RoomAvailable roomAvailable) {
//        this.roomAvailable = roomAvailable;
//    }
//
//    public int getNewPrice() {
//        return newPrice;
//    }
//
//    public void setNewPrice(int newPrice) {
//        this.newPrice = newPrice;
//    }
//
//    public int getOldPrice() {
//        return oldPrice;
//    }
//
//    public void setOldPrice(int oldPrice) {
//        this.oldPrice = oldPrice;
//    }
//
//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }
//}
