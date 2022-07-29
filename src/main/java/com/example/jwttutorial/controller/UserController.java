package com.example.jwttutorial.controller;

import com.example.jwttutorial.dto.UserReqDto;
import com.example.jwttutorial.entity.User;
import com.example.jwttutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserReqDto userReqDto) {
        return ResponseEntity.ok(userService.signup(userReqDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // 요청이 들어오면 함수를 실행하기 전 권한을 검사하는 어노테이션
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
