package com.irnproj.easycollab.module.user.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.user.dto.SignupRequestDto;
import com.irnproj.easycollab.module.user.dto.UserResponseDto;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;
  private final PasswordEncoder passwordEncoder;

  // 회원가입 요청 처리: 중복 검사 → 역할 코드 조회 → 사용자 저장
  public User register(SignupRequestDto request) {
    if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
      throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    if (userRepository.existsByNickname(request.getNickname())) {
      throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
    }

    ComCode role = comCodeRepository.findByCodeTypeAndCode("ROLE", "TEAM_MEMBER")
        .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

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

  // 로그인 ID(loginId)로 사용자 조회
  public User findByUsername(String username) {
    return userRepository.findByLoginId(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  // 로그인 인증 처리: 아이디/비밀번호 확인
  public User authenticate(String loginId, String password) {
    User user = userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new CustomException(ErrorCode.AUTHENTICATION_FAILED));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    return user;
  }

  // 사용자 ID로 마이페이지용 사용자 정보 조회 (DTO 변환)
  public UserResponseDto getUserInfo(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    return UserResponseDto.fromEntity(user);
  }

  // 사용자 ID로 사용자 엔티티 조회 (내부 로직용)
  @Transactional(readOnly = true)
  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  // 사용자가 작성한 댓글 목록 조회
//  public List<CommentResponseDto> getMyComments(Long userId) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));  // CustomException으로 변경
//
//    List<Comment> comments = commentRepository.findByUserId(userId);
//
//    return comments.stream()
//        .map(comment -> CommentResponseDto.builder()
//            .id(comment.getId())
//            .content(comment.getContent())
//            .createdAt(comment.getCreatedAt())
//            .issueId(comment.getIssue().getId())
//            .issueTitle(comment.getIssue().getTitle())
//            .build())
//        .collect(Collectors.toList());
//  }
}