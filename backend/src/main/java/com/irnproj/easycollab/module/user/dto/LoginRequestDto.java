package com.irnproj.easycollab.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto { // 로그인 요청
  private String username;
  private String password;
}