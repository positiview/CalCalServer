package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExRecordDTO {

    private int exrecordId;

    private String userEmail;

    private String exname;

    private Double goalCalorie;

    private Double calorie;

    private LocalDateTime regDate;
}
