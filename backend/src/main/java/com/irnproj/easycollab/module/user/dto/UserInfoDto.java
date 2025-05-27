package com.irnproj.easycollab.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
  private String loginId;
  private String userName;
  private String nickName;
  private String email;
  private String role;
}