package com.example.repository;

import com.example.entity.CourseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseList,Long> {

    @Query("SELECT c FROM CourseList c LEFT JOIN FETCH c.placeList WHERE c.email = :email")
    List<CourseList> findAllByEmailWithPlaces(@Param("email") String email);


    @Query(value = "SELECT DISTINCT cl.course_no, cl.course_name, cl.email,  cl.reg_time FROM course_list cl LEFT JOIN coordinate co ON cl.course_no = co.course_no WHERE cl.email = :email", nativeQuery = true)
    List<CourseList> findCourseListByEmail(@Param("email") String email);

    void deleteByCourseNo(Long course_no);
}
