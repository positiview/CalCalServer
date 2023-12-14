package com.example.service;


import com.example.entity.MemberEntity;
import com.example.entity.RouteRecord;
import com.example.repository.MemberRepository;
import com.example.repository.RouteRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ChartService {

    private MemberRepository memberRepository;
    private RouteRecordRepository routeRecordRepository;

    @Autowired
    public ChartService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    public Map<String, Long> getGenderCounts() {
        List<Object[]> genderCounts = memberRepository.countByGender();
        Map<String, Long> genderMap = new HashMap<>();
        for (Object[] genderCount : genderCounts) {
            if (genderCount[0] != null && genderCount[1] != null) {
                genderMap.put((String) genderCount[0], (Long) genderCount[1]);
            }
        }
        return genderMap;
    }


    public Map<String, Long> getAgeGroupCounts() {
        List<MemberEntity> members = memberRepository.findAll();
        Map<String, Long> ageGroupMap = new TreeMap<>();
        for (MemberEntity member : members) {
            Integer age = member.getAge();
            if (age != null) {
                String ageGroup = getAgeGroup(age);
                ageGroupMap.put(ageGroup, ageGroupMap.getOrDefault(ageGroup, 0L) + 1);
            }
        }
        return ageGroupMap;
    }

    private String getAgeGroup(int age) {
        String ageGroup;
        if (age >= 10 && age <= 19) {
            ageGroup = "10대";
        } else if (age >= 20 && age <= 29) {
            ageGroup = "20대";
        } else if (age >= 30 && age <= 39) {
            ageGroup = "30대";
        } else {
            ageGroup = "기타";
        }
        return ageGroup;
    }

   /* // 1주일간 성별 운동한 칼로리 소모량
    public Map<LocalDate,Integer> getWeekAvgCalorieByGender(String gender) {
        Map<LocalDate,Integer> result = new HashMap<>();

        List<MemberEntity> genderMembers = memberRepository.findbyGender(gender);
        Double count = (double)genderMembers.size();
        Map<LocalDate,Double> totalCalories = totalCalorie(genderMembers);


        return calculateAvg(result,totalCalories,count);
    }

    private Map<LocalDate,Integer> calculateAvg(Map<LocalDate, Integer> result, Map<LocalDate, Double> totalCalories, Double count) {
        for(Map.Entry<LocalDate,Double> entry: totalCalories.entrySet()){
            double avgDay = entry.getValue()/count;
            int roundedAvg = (int)Math.round(avgDay);
            result.put(entry.getKey(),roundedAvg);
        }
        return result;
    }


    private Map<LocalDate,Double> totalCalorie(List<MemberEntity> memberList) {
        Map<LocalDate,Double> totalCalories = new LinkedHashMap<>();



        // 성별따른 유저별 email을 가져온다
        for (MemberEntity member : memberList) {
            String email = member.getEmail();
            // 각 유져의 email로 인덱스하고 운동한 데이터 기록을 가져온다
            List<RouteRecord> records  = routeRecordRepository.findAllByEmail(email);
            // 데이터를 날짜 정보의 역순으로 정렬
            records.sort(Comparator.comparing(RouteRecord::getRegDate).reversed());

            LocalDate today = LocalDate.now();

            for (RouteRecord record : records) {
                LocalDate recordDate = record.getRegDate().toLocalDate();

                totalCalories.put(recordDate, totalCalories.get(recordDate) + record.getCalorie());

                if(recordDate.isBefore(today.minusDays(7))){
                    break;
                }

            }

        }
        return totalCalories;
    }*/

    /*public Map<LocalDate, Integer> getWeekAvgeCalorieByAge() {
        List<MemberEntity> agerBaby = memberRepository.findbyAgeBetween(0,9);
        Map<LocalDate,Integer> result = new HashMap<>();
        Double count = (double)agerBaby.size();
        Map<LocalDate,Double> totalCalories = totalCalorie(agerBaby);
        calculateAvg(result,totalCalories,count);


        List<MemberEntity> ager10s = memberRepository.findbyAgeBetween(10,19);
        Map<LocalDate,Integer> result2 = new HashMap<>();
        Double count2 = (double)ager10s.size();
        Map<LocalDate,Double> totalCalories2 = totalCalorie(ager10s);
        calculateAvg(result2,totalCalories2,count2);

        List<MemberEntity> ager20s = memberRepository.findbyAgeBetween(20,29);
        List<MemberEntity> ager30s = memberRepository.findbyAgeBetween(30,39);
        List<MemberEntity> ager40s = memberRepository.findbyAgeBetween(40,49);
        List<MemberEntity> agerEtc = memberRepository.findbyAgeBetween(40,49);


        return data;
    }*/


}
