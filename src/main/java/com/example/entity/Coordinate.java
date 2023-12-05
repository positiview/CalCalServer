package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Coordinate {
    @Id
    @GeneratedValue
    @Column(name = "Coordinate_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_no")
    private CourseList courseList;

    private double longitude;

    private double latitude;



}
