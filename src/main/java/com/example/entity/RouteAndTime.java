package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RouteAndTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratId;

    private Long latitude;

    private Long longitude;

    private Long time;

    @ManyToOne
    @JoinColumn(name = "routeRecord_email")
    private RouteRecord routeRecord;


}
