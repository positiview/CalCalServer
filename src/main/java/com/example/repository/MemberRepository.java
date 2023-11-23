package com.example.repository;

import com.example.entity.MemberEntity;
import com.example.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByEmail(String email);
}
