package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalDTO {
    private int recordId;

    private String courseName;

    private String exname;

    private Double goalCalorie;

    private Double exGoalCalorie;

    private Double calorie;

    private Double exCalorie;

    private String distance;

    private Double time;

    private Double longitude;

    private Double latitude;

    private LocalDateTime regDate;

    private LocalDateTime exRegDate;

    private int countDays;
}
