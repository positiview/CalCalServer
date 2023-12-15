package com.example.repository;

import com.example.entity.MemberEntity;
import com.example.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);

    @Query("select m from MemberEntity m where m.email like %:keyword% order by m.mno desc")
    Page<MemberEntity> getByemailLike(String keyword, Pageable pageable);

    @Query("select m from MemberEntity m where m.phone like %:keyword% order by m.mno desc")
    Page<MemberEntity> getByphoneLike(String keyword, Pageable pageable);

    MemberEntity findByMno(Long mno);

    @Query("SELECT m.gender, COUNT(m) FROM MemberEntity m GROUP BY m.gender")
    List<Object[]> countByGender();

    @Query("SELECT m.age, COUNT(m) FROM MemberEntity m GROUP BY m.age")
    List<Object[]> countByAge();

    List<MemberEntity> findByGender(String gender);

    List<MemberEntity> findByAgeBetween(int i, int i1);
}
