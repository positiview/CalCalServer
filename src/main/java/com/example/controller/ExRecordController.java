package com.example.controller;

import com.example.model.ExRecordDTO;
import com.example.service.ExRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exrecord")
@AllArgsConstructor
@Log4j2
public class ExRecordController {

    private ExRecordService exRecordService;

    @PostMapping("/exsave")
    public ResponseEntity<String> saveRecord(
                                             @RequestParam String userEmail,
                                             @RequestParam String exname,
                                             @RequestParam Double goalCalorie,
                                             @RequestParam Double calorie){

        log.info("email : " + userEmail);


        exRecordService.saveExRecord(userEmail,exname,goalCalorie,calorie);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

//    @GetMapping("/history")
//    public ResponseEntity<List<RouteRecordDTO>> getHistory(@RequestParam String userEmail){
//        List<RouteRecordDTO> myRouteHistories = ExRecordService.getExRecord(userEmail);
//        log.info("myRoutehistories == " + myRouteHistories);
//        return new ResponseEntity<>(myRouteHistories,HttpStatus.OK);
//    }

//    @GetMapping("/today")
//    public  ResponseEntity<List<CalDTO>> getToday(@RequestParam String userEmail){
//        log.info("getToday 요청 들어옴");
//        List<CalDTO> todayRecords = ExRecordService.getTodayRecord(userEmail);
//        return new ResponseEntity<>(todayRecords,HttpStatus.OK);
//    }
}
