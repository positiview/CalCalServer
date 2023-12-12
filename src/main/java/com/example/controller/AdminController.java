package com.example.controller;

import com.example.entity.AdminMember;
import com.example.entity.MemberEntity;
import com.example.model.AdminMemberDTO;
import com.example.model.MemberDTO;
import com.example.repository.MemberRepository;
import com.example.service.AdminMemberService;
import com.example.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {

    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private MemberRepository memberRepository;
    private AdminMemberService adminMemberService;

    @GetMapping("/adminLogin")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/member/register";
    }

    @PostMapping("/register")
    public String register(AdminMemberDTO adminMemberDTO, Model model) {

        try {
            AdminMember adminMember = AdminMember.createMember(adminMemberDTO, passwordEncoder);
            adminMemberService.saveMember(adminMember);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/register";
        }

        return "redirect:/adminLogin";
    }

    @GetMapping("/auth/adminLogin")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "/member/login";

    }


    @GetMapping("/user")
    public String user(Model model,
                         @RequestParam(defaultValue = "") String searchType,
                         @RequestParam(defaultValue = "") String keyword,
                         @PageableDefault(size = 10, sort = "mno",
                                 direction = Sort.Direction.DESC) Pageable pageable) {

        if (searchType.equals("email")) {
            model.addAttribute("memberList",
                    memberService.getemailList(keyword, pageable));
        }else if (searchType.equals("phone")) {
            model.addAttribute("memberList",
                    memberService.getphoneList(keyword, pageable));
        }else {
            model.addAttribute("memberList",
                    memberService.getMemberList(pageable));
        }

        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "/user/user";
    }

    @GetMapping("/userView")
    public String userView(Long mno, Model model) {
        MemberEntity member = memberRepository.findByMno(mno);


        model.addAttribute("member", member);

        return "user/user_view";
    }

    @GetMapping("/userUpdate")
    public String userUpdate(Long mno, Model model) {
        MemberEntity member = memberRepository.findByMno(mno);

        model.addAttribute("member", member);

        return "user/user_update";
    }

    @PostMapping("/userUpdate")
    public String userUpdate(MemberDTO memberDto) {
        MemberEntity member = new MemberEntity();
        log.info("mno : " + memberDto.getMno());
        member.setMno(memberDto.getMno());
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setPassword2(memberDto.getPassword2());
        member.setPhone(memberDto.getPhone());
        member.setAge(memberDto.getAge());
        member.setGender(memberDto.getGender());
        member.setLength(memberDto.getLength());
        member.setWeight(memberDto.getWeight());


        memberRepository.save(member);

        return "redirect:/userView?mno=" + member.getMno();
    }


    @GetMapping("/userDelete")
    public String userDelete(@RequestParam("mno") Long mno) {
        memberRepository.deleteById(mno);
        return "redirect:/user"; // 삭제 후 사용자를 다시 회원 목록 페이지로 리다이렉트합니다.
    }



}
