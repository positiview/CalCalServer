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
public class TestEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
    private String name;
    private String mainTitle;
    private int contactNum;
}
