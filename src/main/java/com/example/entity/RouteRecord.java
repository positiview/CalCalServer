package com.example.entity;

import com.example.model.RouteAndTimeDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RouteRecord {

    @Id
    private String email;


    @OneToMany(mappedBy = "routeRecord", cascade = CascadeType.ALL)
    private List<RouteAndTime> RouteRecordList = new ArrayList<>();

}
