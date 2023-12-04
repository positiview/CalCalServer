package com.example.controller;

import com.example.entity.CourseList;
import com.example.model.CoordinateDTO;
import com.example.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> saveCourse(@RequestParam("courseName") String courseName, @RequestBody List<CoordinateDTO> list){

        courseService.saveCourseList(courseName,list);
        log.info("course Save !! "+courseName+" <-- 이름 // 리스트 -->" + list);

        return new ResponseEntity<>("Success", HttpStatus.OK);
        
    }

    @GetMapping("/getList")
    public ResponseEntity<List<CourseList>> getAllCourseLists(){
        List<CourseList> courseLists = courseService.getAllCourseLists();
        return new ResponseEntity<>(courseLists, HttpStatus.OK);
    }
}
