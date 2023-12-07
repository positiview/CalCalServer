package com.example.service;

import com.example.entity.Coordinate;
import com.example.entity.CourseList;
import com.example.model.CoordinateDTO;
import com.example.model.CourseListDTO;
import com.example.repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Log4j2
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
            clEntity.getPlaceList().add(coords);
        }

//        clEntity.setCoordinateCount(list.size()); // coordinateCount를 설정

        repository.save(clEntity);

    }
    @Transactional(readOnly = true)
    public List<CourseListDTO> getAllCourseLists(String email) {
        List<CourseListDTO> getCourseLists = new ArrayList<>();
        List<CourseList> lists = repository.findCourseListByEmail(email);

        for (CourseList list : lists) {
            CourseListDTO clDTO = new CourseListDTO();
            List<CoordinateDTO> placeList = new ArrayList<>();

            // placeList 필드에 접근하여 데이터를 가져올 때에만 로딩
            Hibernate.initialize(list.getPlaceList());

            for (Coordinate coords : list.getPlaceList()) {
                CoordinateDTO cDTO = new CoordinateDTO();
                cDTO.setLongitude(coords.getLongitude());
                cDTO.setLatidute(coords.getLatitude());
                log.info("cDTO 의 x : " + cDTO.getLongitude() + ", cDTO의 y: " + cDTO.getLatidute());
                placeList.add(cDTO);
            }

            clDTO.setPlaceList(placeList);
            clDTO.setCourseName(list.getCourseName());
            clDTO.setCourse_no(list.getCourseNo());
            getCourseLists.add(clDTO);
        }

        return getCourseLists;
    }

//     course_no에 해당하는 CourseList 엔티티를 삭제하는 메서드
@Transactional
public void deleteCourseList(Long course_no) {
    try {
        repository.deleteByCourseNo(course_no);
        log.info("Course deleted with courseNo: " + course_no);
    } catch (Exception e) {
        log.error("Failed to delete course with courseNo: " + course_no, e);
        // throw e or handle exception
    }
}
}
