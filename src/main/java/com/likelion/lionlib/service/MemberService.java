package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.dto.SignupRequest;
import com.likelion.lionlib.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void joinProcess(SignupRequest signupRequest) {

        if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        Member newMember = Member.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword()) // 패스워드는 암호화 해야 함
                .build();
        memberRepository.save(newMember);
    }

    public boolean login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        return member.getPassword().equals(password);
    }
}