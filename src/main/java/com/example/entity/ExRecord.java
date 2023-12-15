package com.example.entity;

import com.example.handler.CustomDateTimeConverter;
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
public class ExRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exrecord_id") // 컬럼명 추가
    private Long exrid;

    private String email;

    private String exname;

    private Double goalCalorie;

    private Double calorie;


    @CreatedDate
    @Convert(converter = CustomDateTimeConverter.class)
    private LocalDateTime regDate;

}
