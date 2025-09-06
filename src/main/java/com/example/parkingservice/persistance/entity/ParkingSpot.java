package com.example.parkingservice.persistance.entity;


import com.example.parkingservice.enums.ParkingSpotStatus;
import com.example.parkingservice.enums.ParkingSpotType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "parking_spot")
@Setter
@Getter
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String spotNumber;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private ParkingSpotType type;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_community_id")
    private ResidentialCommunity residentialCommunity;

    @OneToMany(mappedBy = "parkingSpot", cascade = CascadeType.REMOVE)
    private List<Booking> bookings;

}
