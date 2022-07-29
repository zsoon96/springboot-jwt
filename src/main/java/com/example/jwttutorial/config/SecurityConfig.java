package com.example.jwttutorial.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // h2 콘솔 및 favicon 관련 요청은 security 로직 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        , "/favicon.ico"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // HttpServletRequest 사용하는 요청들에 대한 접근제한 설정
                .antMatchers("/api/hello").permitAll() // 해당 api 접근은 인증없이 모두 허용
                .anyRequest().authenticated(); // 이외 나머지 접근은 인증이 필요함
    }
}
