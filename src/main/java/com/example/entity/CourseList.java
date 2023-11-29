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
    @Column(name = "course_no")
    private Long cid;

    private String courseName;

    @OneToMany(mappedBy = "courseList", cascade = CascadeType.ALL)
    private List<Coordinate> dtoList = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;
}
