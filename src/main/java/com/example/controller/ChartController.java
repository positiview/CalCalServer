package com.example.controller;

import com.example.repository.MemberRepository;
import com.example.service.ChartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@Log4j2
public class ChartController {
    private final ChartService chartService;
    private final MemberRepository memberRepository;

    @Autowired
    public ChartController(ChartService chartService, MemberRepository memberRepository) {

        this.chartService = chartService;
        this.memberRepository = memberRepository;
    }


    @GetMapping("/dashboard")
    public String showChart(Model model) {
        long totalMembers = memberRepository.count();

        // Add total member count to the model
        model.addAttribute("totalMembers", totalMembers);
        Map<String, Long> loginCounts = chartService.getLoginCounts();
        model.addAttribute("loginCounts", loginCounts);
        Map<String, Long> genderCounts = chartService.getGenderCounts();
        model.addAttribute("genderCounts", genderCounts);
        Map<String, Long> ageGroupCounts = chartService.getAgeGroupCounts();
        model.addAttribute("ageGroupCounts", ageGroupCounts);





        return "dashboard/dashboard";
    }

    @PostMapping("/dashboardRequest")
    @ResponseBody
    public ResponseEntity<List<Map<LocalDate,Integer>>> getChart(@RequestParam Boolean booleanValue){
            List<Map<LocalDate,Integer>> resultList;

        if(booleanValue){
            // index 0 = 10살 미만 / 1 = 10대 / 2 = 20대 / 3 = 30대 / 4 = 40대 / 5 = 50살 이상
            resultList = chartService.getWeekAvgCalorieByAge();
        }else{
            // index 0 = 여자 , index 1 = 남자
            resultList = chartService.getWeekAvgCalorieByGender();
        }
        log.info("resultList >> " + resultList);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

}
