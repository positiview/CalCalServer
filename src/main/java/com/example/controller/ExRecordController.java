package com.example.controller;

import com.example.model.ExRecordDTO;
import com.example.model.ExerciseDTO;
import com.example.service.ExRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/exrecord")
@AllArgsConstructor
@Log4j2
public class ExRecordController {

    private ExRecordService exRecordService;

    @PostMapping("/exsave")
    public ResponseEntity<List<String>> saveRecord(
            @RequestParam String userEmail,
            @RequestParam String exname,
            @RequestParam Double goalCalorie,
            @RequestParam Double calorie){

        log.info("email : " + userEmail);


        exRecordService.saveExRecord(userEmail,exname,goalCalorie,calorie);

        return new ResponseEntity<>(Collections.singletonList("Success"), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ExRecordDTO>> getExRecord(@RequestParam String email) {
        List<ExRecordDTO> exrecordDTOs = (List<ExRecordDTO>) exRecordService.getExrecord(email);

        if (exrecordDTOs != null && !exrecordDTOs.isEmpty()) {
            return new ResponseEntity<>(exrecordDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
