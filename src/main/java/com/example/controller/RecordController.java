package com.example.controller;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
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
                                             @RequestParam String email,
                                             @RequestParam String courseName){
        log.info("list : " + ratList);
        log.info("email : " + email);

        routeRecordService.saveRouteRecord(ratList,email,courseName);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<RouteRecordDTO>> getHistory(@RequestParam String email){
        List<RouteRecordDTO> myRouteHistories = routeRecordService.getRouteRecord(email);

        return  new ResponseEntity<>(myRouteHistories,HttpStatus.OK);
    }
}
