package com.example.saju.saju.controller;

import com.example.saju.saju.dto.MemberForm;
import com.example.saju.saju.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "login";  // ✅ 내가 만든 login.html 사용
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        if(memberService.login(username, password)) {
            return "redirect:/"; // 로그인 성공 -> 메인화면 이동
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            memberService.register(username, password);
            return "redirect:/login";  // ✅ 가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 실패: " + e.getMessage());
            return "register";
        }
    }

//    // 메인 페이지 (로그인 후)
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
}