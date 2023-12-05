package com.example.repository;

import com.example.entity.CourseList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseList,Long> {
}
