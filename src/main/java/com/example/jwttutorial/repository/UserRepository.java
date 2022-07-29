package com.example.jwttutorial.repository;

import com.example.jwttutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 쿼리가 수행될 때, Lazy가 아닌 Eager 조회로 user와 authorities 정보 한번에 조회
    @EntityGraph(attributePaths = "authorities")
    // username을 통해 user 정보와 authorities 정보도 같이 조회하는 메서드
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
