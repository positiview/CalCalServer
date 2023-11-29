package com.example.model;

import lombok.Data;

import java.util.List;

@Data
public class CourstListDTO {

    private String courseName;

    private List<CoordinateDTO> list;

}
