package com.example.service;

import com.example.entity.Coordinate;
import com.example.entity.CourseList;
import com.example.model.CoordinateDTO;
import com.example.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository repository;

    public void saveCourseList(String courseName,String email, List<CoordinateDTO> list){
        CourseList clEntity = new CourseList();
        clEntity.setCourseName(courseName);
        clEntity.setEmail(email);

        // 저장한 엔티티를 반환받아서 course_no 값을 얻음
        CourseList savedEntity = repository.save(clEntity);

        for(CoordinateDTO cDTO : list) {
            Coordinate coords = new Coordinate();

            coords.setLongitude(cDTO.getLongitude());
            coords.setLatitude(cDTO.getLatidute());
            coords.setCourseList(savedEntity);  // courseList를 설정
            clEntity.getDtoList().add(coords);
        }

        clEntity.setCoordinateCount(list.size()); // coordinateCount를 설정

        repository.save(clEntity);

    }
    public List<CourseList> getAllCourseLists(){
        return repository.findAll();
    }
}
