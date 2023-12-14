package com.example.controller;

import com.example.repository.MemberRepository;
import com.example.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Map;

@Controller
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

        // 여자 평균 1주일간 총 칼로리
        /*Map<LocalDate,Integer> avgCalorieWoman = chartService.getWeekAvgCalorieByGender("female");
        model.addAttribute("avgCalorieWoman",avgCalorieWoman);
        // 남자 평균 1주일간 총 칼로리
        Map<LocalDate,Integer> avgCalorieMan = chartService.getWeekAvgCalorieByGender("male");
        model.addAttribute("avgCalorieMan",avgCalorieMan);*/

        // 나이별 평균 1주일간 총 칼로리
//        Map<LocalDate,Integer> avgCalorieByAge = chartService.getWeekAvgeCalorieByAge();

        return "dashboard/dashboard";
    }

    /*@PostMapping("/dashboardRequest")
    @ResponseBody
    public ResponseEntity<String> getChart(@Requestbody String button, Model model){


        return new ResponseEntity<>("success")
    }*/

}
