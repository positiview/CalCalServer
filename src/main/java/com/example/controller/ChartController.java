package com.example.controller;

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

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/dashboard")
    public String showChart(Model model) {
        Map<String, Long> genderCounts = chartService.getGenderCounts();
        model.addAttribute("genderCounts", genderCounts);
        Map<String, Long> ageGroupCounts = chartService.getAgeGroupCounts();
        model.addAttribute("ageGroupCounts", ageGroupCounts);
        return "/dashboard/dashboard";
    }


}
