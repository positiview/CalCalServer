package com.example.controller;

import com.example.entity.CourseList;
import com.example.model.CoordinateDTO;
import com.example.model.CourseListDTO;
import com.example.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
@Log4j2
public class CourseController {

    private CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCourse(@RequestParam("courseName") String courseName, @RequestParam("email") String email,@RequestBody List<CoordinateDTO> list){

        courseService.saveCourseList(courseName,email,list);
        log.info("course Save !! "+courseName+" <-- 이름 // 리스트 -->" + list);

        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    @GetMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseListDTO>> getAllCourseLists(@RequestParam("email") String email) {
        log.info("email은 : " + email);
        List<CourseListDTO> courseLists = courseService.getAllCourseLists(email);
        log.info("list 뭡니까? " + courseLists);
        return new ResponseEntity<>(courseLists, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{course_no}")
    public ResponseEntity<String> deleteCourse(@PathVariable("course_no") Long course_no) {
        courseService.deleteCourseList(course_no);
        log.info("Course deleted with courseNo: " + course_no);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
