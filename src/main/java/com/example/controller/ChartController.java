package com.example.controller;

import com.example.repository.MemberRepository;
import com.example.service.ChartService;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        return "/dashboard/dashboard";
    }


}
