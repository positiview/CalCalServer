package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_no;


    private String email;

    private String courseName;

    @OneToMany(mappedBy = "courseList", cascade = CascadeType.ALL)
    private List<Coordinate> placeList = new ArrayList<>();

    private int coordinateCount; // 새로 추가된 필드

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;
}
