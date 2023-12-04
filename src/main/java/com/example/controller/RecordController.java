package com.example.controller;

import com.example.model.RouteRecordDTO;
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

    @PostMapping("/save")
    public ResponseEntity<String> saveRecord(@RequestBody List<RouteRecordDTO> recordList, @RequestParam String email){
        log.info("list");

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
