package com.example.repository;

import com.example.entity.CourseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseList,Long> {

    @Query("SELECT c FROM CourseList c LEFT JOIN FETCH c.placeList WHERE c.email = :email")
    List<CourseList> findAllByEmailWithPlaces(@Param("email") String email);


}
