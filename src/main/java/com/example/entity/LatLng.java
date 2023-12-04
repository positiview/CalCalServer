package com.example.entity;

import jakarta.persistence.*;

@Entity
public class LatLng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long llId;

    private Long latitude;

    private Long longitude;

    @ManyToOne
    @JoinColumn(name = "RouteRecord_email")
    private RouteRecord routeRecord;
}
