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
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
