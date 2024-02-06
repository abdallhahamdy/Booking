package com.AlTaraf.Booking.entity.unit.roomAvailable;

import com.AlTaraf.Booking.entity.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_available")
@Data
@AllArgsConstructor
@NoArgsConstructor
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




}
