package com.example.model;

import com.example.entity.RouteAndTime;
import lombok.Data;

import java.util.List;

@Data
public class RouteRecordDTO {

    private String email;

    private String courseName;

    private List<RouteAndTimeDTO> ratList;
}
