package com.example.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eno;
    private String exname;
    private String exicon;
    private String excontent;
    private Integer excal;
    private Integer extime;
    private String email;
}
