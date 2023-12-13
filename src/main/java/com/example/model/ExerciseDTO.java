package com.example.model;

import lombok.Data;

@Data
public class ExerciseDTO {
    private Long eno;

    private String exname;

    private String exicon;

    private String excontent;

    private Integer excal;

    private Integer extime;

    private String email;

    private Boolean exmove;
}
