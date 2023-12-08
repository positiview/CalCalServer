package com.example.entity;

import com.example.model.RouteAndTimeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RouteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id") // 컬럼명 추가
    private Long rid;

    private String email;

    private String courseName;

    private Double calorie;

    @OneToMany(mappedBy = "routeRecord", cascade = CascadeType.ALL)
    private List<RouteAndTime> ratList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime regDate;

}
