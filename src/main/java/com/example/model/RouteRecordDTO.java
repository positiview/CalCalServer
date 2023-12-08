package com.example.model;

import com.example.entity.RouteAndTime;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RouteRecordDTO {


    private String courseName;

    private Double calorie;

    private List<RouteAndTimeDTO> ratList;

    private LocalDateTime regDate;
}
