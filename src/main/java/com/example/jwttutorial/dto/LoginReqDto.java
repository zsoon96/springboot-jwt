package com.example.jwttutorial.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {

    // @Valid 관련 검증 메소드
    @NotNull // 값이 NULL이면 안됨
    @Size(min = 3, max = 50) // 최소 3자에서 최대 50자
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
