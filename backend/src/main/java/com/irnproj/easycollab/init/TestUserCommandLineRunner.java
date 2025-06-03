package com.irnproj.easycollab.init;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("dev") // 개발 환경에서만 실행
@RequiredArgsConstructor
public class TestUserCommandLineRunner implements CommandLineRunner {

  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    if (userRepository.existsByLoginId("testuser")) {
      System.out.println("⚠️ testuser 이미 존재");
      return;
    }

    ComCode role = comCodeRepository.findByCodeTypeAndCode("ROLE", "GUEST")
        .orElseThrow(() -> new RuntimeException("ROLE_GUEST 없음"));

    User user = User.builder()
        .loginId("testuser")
        .username("테스트유저")
        .email("test@example.com")
        .nickname("테스트")
        .password(passwordEncoder.encode("password123"))
        .role(role)
        .build();

    userRepository.save(user);
    System.out.println("✅ 테스트 유저 생성 완료: testuser / password123");
  }
}