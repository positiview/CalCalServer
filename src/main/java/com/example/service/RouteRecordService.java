package com.example.service;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
import com.example.model.CalDTO;
import com.example.model.CoordinateDTO;
import com.example.model.RouteAndTimeDTO;
import com.example.model.RouteRecordDTO;
import com.example.repository.RouteRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class RouteRecordService {

    private RouteRecordRepository routeRecordRepository;

    public void saveRouteRecord(List<RouteAndTimeDTO> list, String email, String cName, Double goalCalorie,Double calorie, String distance){
        RouteRecord rr =new RouteRecord();
        rr.setEmail(email);
        rr.setCourseName(cName);
        rr.setGoalCalorie(goalCalorie);
        rr.setCalorie(calorie);
        rr.setDistance(distance);

        RouteRecord saveEntity = routeRecordRepository.save(rr);

        for(RouteAndTimeDTO ratDTO : list){
            RouteAndTime rat = new RouteAndTime();

            rat.setLongitude(ratDTO.getLongitude());
            rat.setLatitude(ratDTO.getLatitude());
            rat.setTime(ratDTO.getRecordTime());
            rat.setRouteRecord(saveEntity);
            rr.getRatList().add(rat);
        }


        routeRecordRepository.save(rr);

    }
    @Transactional(readOnly = true)
    public List<RouteRecordDTO> getRouteRecord(String email){
        List<RouteRecordDTO> getRecordDTOList = new ArrayList<>();
        List<RouteRecord> recordList = routeRecordRepository.findAllByEmail(email);


        for(RouteRecord rr : recordList){
            RouteRecordDTO routeRecordDTO = new RouteRecordDTO();
            List<RouteAndTimeDTO> ratList = new ArrayList<>();

            Hibernate.initialize(rr.getRatList());

            for(RouteAndTime rat : rr.getRatList()){
                RouteAndTimeDTO ratDTO = new RouteAndTimeDTO();
                ratDTO.setLatitude(rat.getLatitude());
                ratDTO.setLongitude(rat.getLongitude());
                ratDTO.setRecordTime(rat.getTime());
                ratList.add(ratDTO);
            }
            routeRecordDTO.setRatList(ratList);
            routeRecordDTO.setCourseName(rr.getCourseName());
            routeRecordDTO.setGoalCalorie(rr.getGoalCalorie());
            routeRecordDTO.setCalorie(rr.getCalorie());
            routeRecordDTO.setRegDate(rr.getRegDate());
            routeRecordDTO.setDistance(rr.getDistance());
            getRecordDTOList.add(routeRecordDTO);
        }

        return getRecordDTOList;

    }

    public List<CalDTO> getTodayRecord(String userEmail) {
        List<CalDTO> getTodayList = new ArrayList<>();

        List<RouteRecord> records = routeRecordRepository.findAllByEmail(userEmail);
        records.sort(Comparator.comparing(RouteRecord::getRegDate).reversed());

        int count = 0;
        LocalDate today = LocalDate.now();
        for (RouteRecord record : records) {
            LocalDate currentDate = record.getRegDate().toLocalDate();
            if (!currentDate.isEqual(today.minusDays(count))) {
                break;
            }
            count++;
        }
        List<RouteRecord> filteredRecords = records.stream()
                .filter(record -> {
                    LocalDate regDate = record.getRegDate().toLocalDate();
                    return regDate.isEqual(today);
                })
                .toList();
        log.info("Long countDays >> "+ count);

        for(RouteRecord rr : filteredRecords){
            CalDTO calDTO = new CalDTO();

            Hibernate.initialize(rr.getRatList());

            List<RouteAndTime> ratList = rr.getRatList();
            double longitude = 0;
            double latitude = 0;
            if (!ratList.isEmpty()) {
                RouteAndTime firstItem = ratList.get(0);
                longitude = firstItem.getLongitude();
                latitude = firstItem.getLatitude();
                // 이제 longitude와 latitude를 사용할 수 있습니다.
            } else {
                // 리스트가 비어있을 때의 처리
            }
            int lastIndex = ratList.size() - 1;

            if (lastIndex >= 0) {
                RouteAndTime lastRat = ratList.get(lastIndex);
                calDTO.setTime(lastRat.getTime());
            }else{
                // 오늘 리스트가 없음
            }
            calDTO.setLongitude(longitude);
            calDTO.setLatitude(latitude);
            calDTO.setCourseName(rr.getCourseName());
            calDTO.setGoalCalorie(rr.getGoalCalorie());
            calDTO.setCalorie(rr.getCalorie());
            calDTO.setDistance(rr.getDistance());
            calDTO.setRegDate(rr.getRegDate());
            calDTO.setCountDays(count);
            getTodayList.add(calDTO);
            log.info("CalDTO: " + calDTO);
        }
        log.info("getTodayList: " + getTodayList);

        return  getTodayList;
    }
}
