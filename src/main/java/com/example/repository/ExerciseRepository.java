package com.example.repository;

import com.example.entity.ExerciseEntity;
import com.example.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
    ExerciseEntity findByExname(String exname);
}
