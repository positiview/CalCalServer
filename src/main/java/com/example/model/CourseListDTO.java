package com.example.model;

import lombok.Data;

import java.util.List;

@Data
public class CourseListDTO {

    private Long cid;

    private String email;

    private String courseName;

    private List<CoordinateDTO> placeList;


}
