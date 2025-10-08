package com.example.saju.saju.domain;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 PK
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 아이디

    @Column(nullable = false)
    private String password; // 비밀번호 (암호화 저장)

//    @Column(nullable = false)
//    private String email;    // 이메일

    @Column(nullable = false)
    @Builder.Default
    private String role = "USER";
}