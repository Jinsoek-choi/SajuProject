package com.example.saju.saju.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SajuService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model:gpt-5}")
    private String model;

    @Value("${openai.url:https://api.openai.com/v1/chat/completions}")
    private String openaiUrl;

    private WebClient webClient;

    // ✅ Bean 초기화 시점에 WebClient 생성 (apiKey 주입 이후)
    @jakarta.annotation.PostConstruct
    public void initClient() {
        this.webClient = WebClient.builder()
                .baseUrl(openaiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String getSajuFortune(String name, String gender, String birthDate,
                                 String birthTime, String birthPlace, String question) {

        // 🔮 프롬프트 구성
        String prompt = """
                당신은 한국 전통 사주 전문가입니다.
                아래 정보를 바탕으로 운세를 분석해 주세요.
                가능한 한 자연스럽고 현실적인 조언을 중심으로 설명해 주세요.
                
                이름: %s
                성별: %s
                생년월일: %s
                출생시간: %s
                출생지: %s
                질문: %s
                """.formatted(name, gender, birthDate, birthTime, birthPlace, question);

        // 🔧 요청 JSON 구성
        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", "당신은 숙련된 사주 감정가이며, 친절하고 신뢰감 있게 답변해야 합니다."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        Map<String, Object> response;
        try {
            // 🧠 WebClient로 OpenAI API 호출
            response = webClient.post()
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("choices")) {
                return "⚠️ OpenAI 응답을 이해할 수 없습니다.";
            }

            var choices = (List<Map<String, Object>>) response.get("choices");
            if (choices.isEmpty()) {
                return "⚠️ 응답 데이터가 비어 있습니다.";
            }

            var message = (Map<String, Object>) choices.get(0).get("message");
            Object content = message.get("content");
            return content != null ? content.toString().trim() : "⚠️ 응답 내용이 없습니다.";

        } catch (Exception e) {
            return "⚠️ OpenAI API 호출 중 오류 발생: " + e.getMessage();
        }
    }

}
