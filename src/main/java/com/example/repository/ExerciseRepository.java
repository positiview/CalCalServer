package com.example.repository;

import com.example.entity.ExerciseEntity;
import com.example.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    ExerciseEntity findByExname(String exname);

    @Query("select e from ExerciseEntity e where e.exname like %:keyword% order by e.eno desc")
    Page<ExerciseEntity> getByexnameLike(String keyword, Pageable pageable);

    ExerciseEntity findByEno(Long eno);
}
