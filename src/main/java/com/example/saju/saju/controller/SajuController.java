package com.example.saju.saju.controller;

import com.example.saju.saju.service.SajuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SajuController {

    private final SajuService sajuService;


    // 메인 입력 폼
    @PostMapping("/saju")
    public String processSaju(@RequestParam String name,
                              @RequestParam String gender,
                              @RequestParam String birthDate,
                              @RequestParam String birthTime,
                              @RequestParam String birthPlace,
                              @RequestParam String question,
                              Model model) {

        // 🧠 OpenAI API 호출 결과
        String result = sajuService.getSajuFortune(name, gender, birthDate, birthTime, birthPlace, question);

        // 화면에 표시할 메시지 (선택사항)
        String message = name + "님의 사주 결과가 도착했습니다.";

        // 📦 모델에 결과 데이터 담기
        model.addAttribute("message", message);
        model.addAttribute("result", result);
        model.addAttribute("name", name);
        model.addAttribute("gender", gender);
        model.addAttribute("birthDate", birthDate);
        model.addAttribute("birthTime", birthTime);
        model.addAttribute("birthPlace", birthPlace);
        model.addAttribute("question", question);

        return "saju-result";
    }
}
