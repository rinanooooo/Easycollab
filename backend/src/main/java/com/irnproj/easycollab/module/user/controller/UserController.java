package com.irnproj.easycollab.module.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  // 인증된 사용자 정보 확인용
  @GetMapping("/me")
  public String getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
    return "로그인된 사용자: " + userDetails.getUsername();
  }
}