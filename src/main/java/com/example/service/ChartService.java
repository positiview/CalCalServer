package com.example.service;


import com.example.entity.MemberEntity;
import com.example.entity.RouteRecord;
import com.example.repository.ExRecordRepository;
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
    private ExRecordRepository exRecordRepository;

    @Autowired
    public ChartService(MemberRepository memberRepository, RouteRecordRepository routeRecordRepository, ExRecordRepository exRecordRepository) {
        this.memberRepository = memberRepository;
        this.routeRecordRepository = routeRecordRepository;
        this.exRecordRepository = exRecordRepository; // ExRecordRepository 주입
    }

    public Map<String, Long> getLoginCounts() {
        List<MemberEntity> members = memberRepository.findAll();

        Set<String> loggedInMembers = new HashSet<>();
        for (MemberEntity member : members) {
            String email = member.getEmail();
            if (routeRecordRepository.existsByEmail(email) || exRecordRepository.existsByEmail(email)) {
                loggedInMembers.add(email); // 이메일이 RouteRecord 혹은 ExRecord에 있으면 Set에 추가
            }
        }

        long countLoggedIn = loggedInMembers.size(); // 접속한 회원 수는 Set의 크기
        long countNotLoggedIn = members.size() - countLoggedIn; // 접속하지 않은 회원 수는 전체 회원 수에서 접속한 회원 수를 뺀 값

        Map<String, Long> loginMap = new HashMap<>();
        loginMap.put("접속한 회원", countLoggedIn);
        loginMap.put("접속하지 않은 회원", countNotLoggedIn);
        return loginMap;
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

    // 성별/요일별 총 칼로리 평균
    public List<Map<LocalDate,Integer>> getWeekAvgCalorieByGender(){
        List<Map<LocalDate,Integer>> result = new ArrayList<>();

        Map<LocalDate,Integer> woman = getWeekAvgCal("woman");
        Map<LocalDate,Integer> man = getWeekAvgCal("man");
        result.add(woman);
        result.add(man);
        return result;
    }

    // 1주일간 성별 운동한 칼로리 소모량
    public Map<LocalDate,Integer> getWeekAvgCal(String gender) {
        Map<LocalDate,Integer> result = new HashMap<>();

        List<MemberEntity> genderMembers = memberRepository.findByGender(gender);
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
    }


    public List<Map<LocalDate, Integer>> getWeekAvgCalorieByAgeRange(int startAge, int endAge) {
        List<Map<LocalDate, Integer>> totalAvgCalories = new ArrayList<>();
        Map<LocalDate, Integer> result = new HashMap<>();

        List<MemberEntity> members = memberRepository.findByAgeBetween(startAge, endAge);
        Double count = (double) members.size();
        Map<LocalDate, Double> totalCalories = totalCalorie(members);
        Map<LocalDate, Integer> weekAvgCalorie = calculateAvg(result, totalCalories, count);

        totalAvgCalories.add(weekAvgCalorie);
        return totalAvgCalories;
    }

    // 나이/요일별 총 칼로리 평균
    public List<Map<LocalDate, Integer>> getWeekAvgCalorieByAge() {
        List<Map<LocalDate, Integer>> totalAvgCalories = new ArrayList<>();

        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(0, 9));
        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(10, 19));
        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(20, 29));
        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(30, 39));
        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(40, 49));
        totalAvgCalories.addAll(getWeekAvgCalorieByAgeRange(50, 99));

        return totalAvgCalories;
    }


}
