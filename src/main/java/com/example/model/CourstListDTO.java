package com.example.model;

import lombok.Data;

import java.util.List;

@Data
public class CourstListDTO {

    private Long cid;

    private String courseName;

    private List<CoordinateDTO> list;

}
