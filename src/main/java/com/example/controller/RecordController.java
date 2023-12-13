package com.example.controller;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
import com.example.model.CalDTO;
import com.example.model.RouteAndTimeDTO;
import com.example.model.RouteRecordDTO;
import com.example.service.RouteRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@AllArgsConstructor
@Log4j2
public class RecordController {

    private RouteRecordService routeRecordService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRecord(@RequestBody List<RouteAndTimeDTO> ratList,
                                             @RequestParam String userEmail,
                                             @RequestParam String courseName,
                                             @RequestParam Double goalCalorie,
                                             @RequestParam Double calorie,
                                             @RequestParam String distance){
        log.info("list : " + ratList);
        log.info("email : " + userEmail);


        routeRecordService.saveRouteRecord(ratList,userEmail,courseName,goalCalorie,calorie,distance);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<RouteRecordDTO>> getHistory(@RequestParam String userEmail){
        List<RouteRecordDTO> myRouteHistories = routeRecordService.getRouteRecord(userEmail);
        log.info("myRoutehistories == " + myRouteHistories);
        return new ResponseEntity<>(myRouteHistories,HttpStatus.OK);
    }

    @GetMapping("/today")
    public  ResponseEntity<List<CalDTO>> getToday(@RequestParam String userEmail){
        log.info("getToday 요청 들어옴");
        List<CalDTO> todayRecords = routeRecordService.getTodayRecord(userEmail);
        return new ResponseEntity<>(todayRecords,HttpStatus.OK);
    }
}
