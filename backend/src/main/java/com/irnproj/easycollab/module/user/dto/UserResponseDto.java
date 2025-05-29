package com.irnproj.easycollab.module.user.dto;

import com.irnproj.easycollab.module.user.entity.User;
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
  private String userName;
  private String nickName;
  private String email;
  private String role;

  public static UserResponseDto fromEntity(User user) {
    return UserResponseDto.builder()
        .loginId(user.getLoginId())
        .userName(user.getUsername())
        .nickName(user.getNickname())
        .email(user.getEmail())
        .role(user.getRole().getCode())  // 또는 .getName()
        .build();
  }
}