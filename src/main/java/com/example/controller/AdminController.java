package com.example.controller;

import com.example.entity.AdminMember;
import com.example.entity.ExerciseEntity;
import com.example.entity.MemberEntity;
import com.example.model.AdminMemberDTO;
import com.example.model.ExerciseDTO;
import com.example.model.MemberDTO;
import com.example.repository.ExerciseRepository;
import com.example.repository.MemberRepository;
import com.example.service.AdminMemberService;
import com.example.service.ExerciseService;
import com.example.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {

    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private MemberRepository memberRepository;
    private AdminMemberService adminMemberService;
    private ExerciseService exerciseService;
    private ExerciseRepository exerciseRepository;

    @GetMapping("/adminLogin")
    public String login() {
        return "member/login";
    }

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String register(AdminMemberDTO adminMemberDTO, Model model) {

        try {
            AdminMember adminMember = AdminMember.createMember(adminMemberDTO, passwordEncoder);
            adminMemberService.saveMember(adminMember);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/register";
        }

        return "redirect:/adminLogin";
    }

    @PostMapping("/adminLogin") // GET에서 POST로 변경
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "member/login";
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

        return "user/user";
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

    @GetMapping("/exercise")
    public String exercise(Model model,
                       @RequestParam(defaultValue = "") String searchType,
                       @RequestParam(defaultValue = "") String keyword,
                       @PageableDefault(size = 10, sort = "eno",
                               direction = Sort.Direction.DESC) Pageable pageable) {

        if (searchType.equals("exname")) {
            model.addAttribute("exerciseList",
                    exerciseService.getexnameList(keyword, pageable));
        }else {
            model.addAttribute("exerciseList",
                    exerciseService.getExerciseList(pageable));
        }

        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "exercise/exercise";
    }

    @GetMapping("/exerciseView")
    public String exerciseView(Long eno, Model model) {
        ExerciseEntity exercise = exerciseRepository.findByEno(eno);


        model.addAttribute("exercise", exercise);
        model.addAttribute("imagePath", exercise.getExicon().replace("\\", "/"));

        return "exercise/exercise_view";
    }

    @GetMapping("/exerciseUpdate")
    public String exerciseUpdate(Long eno, Model model) {
        ExerciseEntity exercise = exerciseRepository.findByEno(eno);

        model.addAttribute("exercise", exercise);

        return "exercise/exercise_update";
    }
    @PostMapping("/exerciseUpdate")
    public String exerciseUpdate(ExerciseDTO exerciseDTO, @RequestParam(value = "exicon", required = false) MultipartFile exicon) {
        ExerciseEntity exercise = exerciseRepository.findByEno(exerciseDTO.getEno());

        // 기존 정보 복사
        exercise.setEmail(exerciseDTO.getEmail());
        exercise.setExcal(exerciseDTO.getExcal());
        exercise.setExcontent(exerciseDTO.getExcontent());
        exercise.setExname(exerciseDTO.getExname());
        exercise.setExtime(exerciseDTO.getExtime());
        exercise.setExmove(exerciseDTO.getExmove());

        // 이미지 파일 처리
        MultipartFile exiconFile = exerciseDTO.getExiconFile();
        if (exiconFile != null && !exiconFile.isEmpty()) {
            String filename = exiconFile.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            try (InputStream input = exiconFile.getInputStream()) {
                Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            exercise.setExicon(path.toString());
        }

        exerciseRepository.save(exercise);

        return "redirect:/exerciseView?eno=" + exercise.getEno();
    }

    @GetMapping("/exerciseDelete")
    public String exerciseDelete(@RequestParam("eno") Long eno) {
        exerciseRepository.deleteById(eno);
        return "redirect:/exercise"; // 삭제 후 사용자를 다시 회원 목록 페이지로 리다이렉트합니다.
    }

    @GetMapping("/exerciseRegister")
    public String exerciseRegister(Model model, HttpServletRequest request) {
        // 현재 로그인된 사용자의 이메일을 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        if (authentication != null) {
            System.out.println("Name: " + authentication.getName());
        }
        String currentUserName = authentication.getName();

        // 이메일을 모델에 추가합니다.
        model.addAttribute("email", currentUserName);

        return "exercise/exercise_register";
    }

    @PostMapping("/exerciseRegister")
    public String exerciseRegister(
            @RequestParam("exname") String exname,
            @RequestParam("exicon") MultipartFile exicon,
            @RequestParam("excontent") String excontent,
            @RequestParam("excal") Integer excal,
            @RequestParam("extime") Integer extime,
            @RequestParam("email") String email
    ) {
        ExerciseEntity exercise = new ExerciseEntity();

        // 파일을 저장하고, 그 경로를 가져옵니다.
        String filePath = saveFile(exicon);

        exercise.setEmail(email);
        exercise.setExcal(excal);
        exercise.setExcontent(excontent);
        // 파일 경로를 설정합니다.
        exercise.setExicon(filePath);
        exercise.setExname(exname);
        exercise.setExtime(extime);

        exerciseRepository.save(exercise);

        return "redirect:/exercise";
    }


    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get("uploads/" + fileName);
            // 디렉토리를 확인하고, 없다면 생성합니다.
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
        }
    }


}

