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

    private Double latitude;

    private Double longitude;

    private Double time;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private RouteRecord routeRecord;


}
