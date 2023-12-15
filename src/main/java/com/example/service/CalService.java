package com.example.service;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
import com.example.model.CalDTO;
import com.example.model.ExRecordDTO;
import com.example.model.RouteAndTimeDTO;
import com.example.model.RouteRecordDTO;
import com.example.repository.ExRecordRepository;
import com.example.repository.RouteRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CalService {

    private RouteRecordRepository routeRecordRepository;
    private ExRecordRepository exRecordRepository;
    private final ExRecordService exRecordService;
    private final CalService routeRecordService;



//
//    Map<String, ExRecordDTO> exRecordMap = exRecordService.getAllExRecords()
//            .stream()
//            .collect(Collectors.toMap(ExRecordDTO::getUserEmail, Function.identity()));
//
//    Map<String, RouteRecordDTO> routeRecordMap = routeRecordService.getAllRouteRecords()
//            .stream()
//            .collect(Collectors.toMap(RouteRecordDTO::getUserEmail, Function.identity()));

    // 오늘 날짜 총 칼로리 소모량 데이터
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
            calDTO.setRecordId(rr.getRid().intValue());
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


    // 선택한 날짜 총 칼로리 소모량 데이터
    public List<CalDTO> getSelectedDayRecord(String userEmail, String selectedDay) {
        List<CalDTO> getTodayList = new ArrayList<>();

        List<RouteRecord> records = routeRecordRepository.findAllByEmail(userEmail);
        records.sort(Comparator.comparing(RouteRecord::getRegDate).reversed());

        int count = 0;
        LocalDate choiceDay = LocalDate.parse(selectedDay);
        for (RouteRecord record : records) {
            LocalDate currentDate = record.getRegDate().toLocalDate();
            if (!currentDate.isEqual(choiceDay.minusDays(count))) {
                break;
            }
            count++;
        }
        List<RouteRecord> filteredRecords = records.stream()
                .filter(record -> {
                    LocalDate regDate = record.getRegDate().toLocalDate();
                    return regDate.isEqual(choiceDay);
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
            calDTO.setRecordId(rr.getRid().intValue());
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
