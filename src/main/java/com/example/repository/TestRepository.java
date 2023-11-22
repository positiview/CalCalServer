package com.example.repository;

import com.example.entity.TestEntity;
import com.example.model.TestDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

}
