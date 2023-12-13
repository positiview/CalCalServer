package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ExerciseDTO {
    private Long eno;

    private String exname;

    @JsonIgnore
    private String exicon;

    private String excontent;

    private Integer excal;

    private Integer extime;

    private String email;

    private Boolean exmove;

    private MultipartFile exiconFile;  // 새로운 필드
}
