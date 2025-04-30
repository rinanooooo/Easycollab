package com.irnproj.easycollab.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
  private String username;
  private String message;
}