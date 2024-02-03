package com.AlTaraf.Booking.entity.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.Unit;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_available")
public class RoomAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_AVAILABLE_ID")
    private Long id;

    @Column(name = "ROOM_AVAILABLE_NAME")
    private String name;

    @Column(name = "ROOM_AVAILABLE_NAME_ARABIC")
    private String arabicName;

    @OneToMany(mappedBy = "roomAvailable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomDetails> roomDetailsList = new ArrayList<>();


//    @ElementCollection
//    @CollectionTable(name = "room_type_details", joinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID"))
//    private List<RoomTypeDetails> roomTypeDetailsList = new ArrayList<>();

//    @OneToMany(mappedBy = "roomAvailable", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<RoomDetails> roomDetailsList = new ArrayList<>();


    public RoomAvailable() {
    }

    public RoomAvailable(Long id, String name, String arabicName, List<RoomDetails> roomDetailsList) {
        this.id = id;
        this.name = name;
        this.arabicName = arabicName;
        this.roomDetailsList = roomDetailsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public List<RoomDetails> getRoomDetailsList() {
        return roomDetailsList;
    }

    public void setRoomDetailsList(List<RoomDetails> roomDetailsList) {
        this.roomDetailsList = roomDetailsList;
    }
}
