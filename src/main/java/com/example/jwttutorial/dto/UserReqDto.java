package com.example.jwttutorial.dto;

import com.example.jwttutorial.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    // 해당 필드는 오직 쓰려는 경우에만 접근 허용하는 어노테이션
    // 요청을 처리할 때 사용되고, 응답 결과에는 해당 필드에 대한 데이터 제외하고 응답
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max =50)
    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserReqDto from(User user) {
        // api/user에서 아래의 if문을 타버림..db에는 데이터가 있는데 어째서 null일까...
        if(user == null) return null;

        return UserReqDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
