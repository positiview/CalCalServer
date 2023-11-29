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

    public void saveCourseList(String courseName, List<CoordinateDTO> list){
        CourseList clEntity = new CourseList();
        clEntity.setCourseName(courseName);

        for(CoordinateDTO cDTO : list) {
            Coordinate coords = new Coordinate();

            coords.setLongitude(cDTO.getLongitude());
            coords.setLatitude(cDTO.getLatitude());
            clEntity.getDtoList().add(coords);
        }

        repository.save(clEntity);

    }
}
