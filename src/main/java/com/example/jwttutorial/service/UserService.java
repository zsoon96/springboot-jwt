package com.example.jwttutorial.service;

import com.example.jwttutorial.dto.UserReqDto;
import com.example.jwttutorial.entity.Authority;
import com.example.jwttutorial.entity.User;
import com.example.jwttutorial.repository.UserRepository;
import com.example.jwttutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

// 회원가입, 유저정보조회 등에 필요한 로직
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    @Transactional
    public User signup (UserReqDto userReqDto) {
        // 기존 정보가 있는지 검증
        if ( userRepository.findOneWithAuthoritiesByUsername(userReqDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 없는 정보면 권한 정보 만들어주고
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 권한 정보를 이용하여 유저 정보 만들어서
        User user = User.builder()
                .username(userReqDto.getUsername())
                .password(passwordEncoder.encode(userReqDto.getPassword()))
                .nickname(userReqDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        // db에 저
        return userRepository.save(user);
    }

    // 유저/권한 정보 가져오는 메소드 1 - username 기준으로 정보 조회
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 유저/권한 정보 가져오는 메소드 2 - SecurityContext에정저장된 username 정보만 조회
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
