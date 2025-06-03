package com.irnproj.easycollab.module.user.repository;

import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLoginId(String loginId); // loginId로 사용자
  boolean existsByEmail(String email); // 이메일 중복 확인
  boolean existsByNickname(String nickname); // 닉네임 중복 확인

  boolean existsByLoginId(String testuser);
}