package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteRecord {

    @Id
    private String email;


    @OneToMany(mappedBy = "RouteRecord", cascade = CascadeType.ALL)
    private List<LatLng> recordList;


}
