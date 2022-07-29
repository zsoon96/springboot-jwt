package com.example.jwttutorial.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // HttpServletRequest 사용하는 요청들에 대한 접근제한 설정
                .antMatchers("/api/hello").permitAll() // 해당 api 접근은 인증없이 모두 허용
                .anyRequest().authenticated(); // 이외 나머지 접근은 인증이 필요함
    }
}
