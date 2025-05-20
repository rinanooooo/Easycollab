package com.irnproj.easycollab.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto { // 로그인 응답
  private String username;
  private String token;
}