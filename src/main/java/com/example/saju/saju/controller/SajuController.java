package com.example.saju.saju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SajuController {

    // 메인 입력 폼
    @GetMapping("/")
    public String showForm() {
        return "saju-form";  // templates/saju-form.html
    }

    // 폼 제출 처리
    @PostMapping("/saju")
    public String processSaju(@RequestParam String name,
                              @RequestParam String gender,
                              @RequestParam String birthDate,
                              @RequestParam String birthplace,
                              @RequestParam String question,
                              Model model) {
        // TODO: 나중에 GPT API 호출 로직 추가 가능
        String message = name + "님의 사주 요청이 접수되었습니다.";
        model.addAttribute("message", message);
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("birthplace", birthplace);
        model.addAttribute("question", question);
        return "saju-result";  // 결과 페이지로 이동
    }
}
