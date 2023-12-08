package com.example.repository;

import com.example.entity.MemberEntity;
import com.example.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);

    @Query("select m from MemberEntity m where m.email like %:keyword% order by m.mno desc")
    Page<MemberEntity> getByemailLike(String keyword, Pageable pageable);

    @Query("select m from MemberEntity m where m.phone like %:keyword% order by m.mno desc")
    Page<MemberEntity> getByphoneLike(String keyword, Pageable pageable);

}
