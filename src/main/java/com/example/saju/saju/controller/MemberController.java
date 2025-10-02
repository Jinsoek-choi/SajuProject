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

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute MemberForm form) {
        memberService.register(form);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // 로그인 성공 시 이동
    }
}