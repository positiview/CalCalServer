package com.example.model;

import jakarta.persistence.Table;
import lombok.*;

@Data
public class CoordinateDTO {
    private Long course_no;
    private double longitude;
    private double latidute;

}
