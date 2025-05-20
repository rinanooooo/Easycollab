package com.irnproj.easycollab.module.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto { // 회원가입 요청
  private String username;
  private String nickname;
  private String email;
  private String password;

  public SignupRequestDto(String username, String nickname, String email, String password) {
    this.username = username;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
  }
}