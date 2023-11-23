package com.example.controller;

import com.example.entity.MemberEntity;
import com.example.model.MemberDTO;
import com.example.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Log4j2
public class MemberController {

    private MemberService memberService;

    @GetMapping("/members")
    public String member(String id){
        return "index";
    }

    @PostMapping("/members")
    public ResponseEntity<String> member(@RequestBody MemberDTO memberDTO){
        memberService.saveMember(memberDTO);
//        log.info("가져온 id : "+ id);
        log.info("memberDTO : "+ memberDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO) {
        // 로그인 로직 구현
        boolean loginSuccessful = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());

        if (loginSuccessful) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failure", HttpStatus.UNAUTHORIZED);
        }
    }
}