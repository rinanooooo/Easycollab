package com.irnproj.easycollab.module.user.service;

import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.user.dto.SignupRequestDto;
import com.irnproj.easycollab.module.user.dto.UserInfoDto;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.lang.IllegalArgumentException;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원가입 로직
  public User register(SignupRequestDto request) {
    // 1. 중복 체크
    if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
      throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
    }
    if (userRepository.existsByNickname(request.getNickname())) {
      throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
    }

    // 2. 역할 코드 조회
    ComCode role = comCodeRepository.findByCodeTypeAndCode("ROLE", "팀원")
        .orElseThrow(() -> new IllegalArgumentException("역할 코드를 찾을 수 없습니다."));

    // 3. 유저 엔티티 생성
    User user = User.builder()
        .loginId(request.getLoginId())
        .username(request.getUsername())
        .email(request.getEmail())
        .nickname(request.getNickname())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(role)
        .build();

    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByLoginId(username)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
  }

  public User authenticate(String loginId, String password) {
    User user = userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    return user;
  }

  public UserInfoDto getUserInfo(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    return new UserInfoDto(
        user.getLoginId(),
        user.getUsername(),
        user.getNickname(),
        user.getEmail(),
        user.getRole().getCode()  // Code 객체에서 문자열 추출
    );
  }
}