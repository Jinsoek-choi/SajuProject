package com.example.saju.saju.service;


import com.example.saju.saju.domain.Member;
import com.example.saju.saju.dto.MemberForm;
import com.example.saju.saju.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(MemberForm form) {
        Member member = Member.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .email(form.getEmail())
                .build();
        return memberRepository.save(member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }
}