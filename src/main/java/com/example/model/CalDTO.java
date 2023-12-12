package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalDTO {
    private String courseName;

    private Double goalCalorie;

    private Double calorie;

    private String distance;

    private Double time;

    private Double longitude;

    private Double latitude;

    private LocalDateTime regDate;

    private int countDays;
}
