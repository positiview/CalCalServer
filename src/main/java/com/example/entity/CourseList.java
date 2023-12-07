package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "course_no") // 컬럼명 추가
    private Long courseNo;


    private String email;

    private String courseName;

    @JsonIgnore
    @OneToMany(mappedBy = "courseList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Coordinate> placeList = new ArrayList<>();

//    private int coordinateCount; // 새로 추가된 필드

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;
}
