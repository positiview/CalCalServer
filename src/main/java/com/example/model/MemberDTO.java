package com.example.model;

import lombok.Data;

@Data
public class MemberDTO {
    private Long mno;

    private String email;

    private String phone;

    private String password;

    private String password2;

    private Integer weight;

    private Integer length;

    private Integer age;

    private String gender;

    private Integer goalcal;
}
