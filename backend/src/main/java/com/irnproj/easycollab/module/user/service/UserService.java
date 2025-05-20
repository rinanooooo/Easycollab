package com.irnproj.easycollab.module.user.service;

import com.irnproj.easycollab.module.user.dto.SignupRequestDto;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원 가입
  public User register(SignupRequestDto request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    }

    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
    }

    if (userRepository.findByNickname(request.getNickname()).isPresent()) {
      throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
    }

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .nickname(request.getNickname())
        .password(passwordEncoder.encode(request.getPassword()))
        .role("ROLE_USER")
        .build();

    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
  }
}