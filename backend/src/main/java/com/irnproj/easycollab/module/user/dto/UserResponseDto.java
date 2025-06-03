package com.irnproj.easycollab.module.user.dto;

import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private String loginId;
  private String username;
  private String nickname;
  private String email;
  private String role;
  private String profileImageUrl;

  public static UserResponseDto fromEntity(User user) {
    return UserResponseDto.builder()
        .loginId(user.getLoginId())
        .username(user.getUsername())
        .nickname(user.getNickname())
        .email(user.getEmail())
        .role(user.getRole().getCode())  // 또는 .getName()
        .profileImageUrl(user.getProfileImageUrl())
        .build();
  }
}