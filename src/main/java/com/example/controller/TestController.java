package com.example.controller;

import com.example.model.TestDTO;
import com.example.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class TestController {

    private TestService testService;

    @GetMapping("/tests")
    public String test(String id){
        return "index";
    }

    @PostMapping("/tests")
    public ResponseEntity<String> test(@RequestBody TestDTO testDTO){
        testService.saveTest(testDTO);
//        log.info("가져온 id : "+ id);
        log.info("testDTO : "+ testDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
