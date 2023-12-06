package com.example.entity;

import com.example.model.RouteAndTimeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RouteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String email;

    private String courseName;

    @OneToMany(mappedBy = "routeRecord", cascade = CascadeType.ALL)
    private List<RouteAndTime> ratList;

}
