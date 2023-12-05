package com.example.controller;

import com.example.model.ExerciseDTO;
import com.example.model.MemberDTO;
import com.example.service.ExerciseService;
import com.example.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class ExerciseController {

    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public String exercise(String id){
        return "index";
    }

    @PostMapping("/exercises")
    public ResponseEntity<String> exercise(@RequestBody ExerciseDTO exerciseDTO){
        exerciseService.saveExercise(exerciseDTO);
//        log.info("가져온 id : "+ id);
        log.info("exerciseDTO : "+ exerciseDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody ExerciseDTO exerciseDTO) {
//        // 로그인 로직 구현
//        boolean loginSuccessful = exerciseService.login(memberDTO.getEmail(), memberDTO.getPassword());
//
//        if (loginSuccessful) {
//            return new ResponseEntity<>("Success", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failure", HttpStatus.UNAUTHORIZED);
//        }
//    }
// 흔적기관


    @PutMapping("/updateExerciseData")
    public ResponseEntity<String> set(@RequestBody  ExerciseDTO exerciseDTO) {

        boolean updateSuccessful = exerciseService.updateExerciseData(exerciseDTO);


        if (updateSuccessful) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getExerciseData")
    public ResponseEntity<ExerciseDTO> getExerciseData(@RequestParam String exname) {
        ExerciseDTO exerciseDTO = ExerciseService.getExerciseData(exname);

        if (exerciseDTO != null) {
            return new ResponseEntity<>(exerciseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
