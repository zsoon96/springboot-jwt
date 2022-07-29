package com.example.jwttutorial.controller;

import com.example.jwttutorial.dto.LoginReqDto;
import com.example.jwttutorial.dto.TokenResDto;
import com.example.jwttutorial.jwt.JwtFilter;
import com.example.jwttutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResDto> authorize(@Valid @RequestBody LoginReqDto loginReqDto) {

        // username과 password를 가지고 토큰 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());

        // authenticationToken 이용하여 authentication 객체 생성
        // athenticate 메소드가 실행이 될 때 service의 loadUserByUsername 메소드 실행하여 db에서 해당 정보 가져옴?
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        // 토큰 헤더에 담아주기
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORITIES_HEADER, "Bearer " + jwt);

        // 토큰 바디에 담아주기
        return new ResponseEntity<>(new TokenResDto(jwt), headers, HttpStatus.OK);
    }
}
