package com.example.controller;

import com.example.entity.AdminMember;
import com.example.model.AdminMemberDTO;
import com.example.service.AdminMemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class AdminController {

    private PasswordEncoder passwordEncoder;
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
}
