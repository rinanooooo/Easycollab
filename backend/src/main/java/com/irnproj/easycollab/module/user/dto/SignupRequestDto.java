package com.irnproj.easycollab.module.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto { // 회원가입 요청
  @NotBlank
  private String loginId;
  @NotBlank private String username;
  @NotBlank private String nickname;
  @NotBlank @Email
  private String email;
  @NotBlank private String password;

  public SignupRequestDto(String loginId, String username, String nickname, String email, String password) {
    this.loginId = loginId;
    this.username = username;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
  }
}