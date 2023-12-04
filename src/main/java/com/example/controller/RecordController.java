package com.example.controller;

import com.example.model.RouteAndTimeDTO;
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
    public ResponseEntity<String> saveRecord(@RequestBody List<RouteAndTimeDTO> rtList, @RequestParam String email){
        log.info("list : " + rtList);
        log.info("email : " + email);

        routeRecordService.saveRouteRecord(rtList,email);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
