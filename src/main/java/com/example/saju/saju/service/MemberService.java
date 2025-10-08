package com.example.saju.saju.service;


import com.example.saju.saju.domain.Member;
import com.example.saju.saju.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(String username, String rawPassword) {
        // 중복 아이디 방지 (선택)
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role("USER")
                .build();

        return memberRepository.save(member);
    }

    // 로그인 검증 추가
    public boolean  login(String username, String rawPassword) {
        Optional<Member> opt = memberRepository.findByUsername(username);
        if (opt.isEmpty()) return false; // 아이디 없음

        Member member = opt.get();
        // 비밀번호 비교 (암호화된 비번과 평문 비교)
        return passwordEncoder.matches(rawPassword, member.getPassword());

    }


}