package com.example.jwttutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터들(결과 데이터 / Http 상태코드 / 응답 메세지)을 포함한 클래스
        // ResponseEntity 구조
        // 1. HttpStatus : 상태 코드
        // 2. HttpHeaders : 웹서버가 웹브라우저에 응답하는 메세지 (요청/응답에 대한 요구사항)
        // 3. HttpBody : 요청에 대한 결과 데이터
    // ResponseEntity 클래스를 사용시 장점
    // 1. 상태코드 / 헤더값 / 결과값을 필요에 맞게 웹브라우저에게 넘겨줄 수 있고.
    // 2. 에러코드도 섬세하게 설정해서 보내줄 수 있음
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello"); // 상태코드와 함께 body 전송 (정적 팩토리 메소드 사용)
    }
}
