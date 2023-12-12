package com.example.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RouteRecordDTO {

    private int recordId;

    private String courseName;

    private Double goalCalorie;

    private Double calorie;

    private String distance;

    private List<RouteAndTimeDTO> ratList;

    private LocalDateTime regDate;
}
