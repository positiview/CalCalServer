package com.example.model;

import lombok.Data;

import java.util.List;

@Data
public class CourstListDTO {

    private Long course_no;

    private String courseName;

    private List<CoordinateDTO> placeList; // 필드명 변경

}
