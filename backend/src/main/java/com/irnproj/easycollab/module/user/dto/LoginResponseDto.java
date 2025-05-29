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
public class LoginResponseDto {
  private Long id;
  private String loginId;
  private String username;
  private String token;

  public static LoginResponseDto fromEntity(User user, String token) {
    return LoginResponseDto.builder()
        .id(user.getId())
        .loginId(user.getLoginId())
        .username(user.getUsername())
        .token(token)
        .build();
  }
}