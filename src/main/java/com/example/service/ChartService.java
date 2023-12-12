package com.example.service;


import com.example.entity.MemberEntity;
import com.example.model.MemberDTO;
import com.example.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ChartService {

    private MemberRepository memberRepository;

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

}
