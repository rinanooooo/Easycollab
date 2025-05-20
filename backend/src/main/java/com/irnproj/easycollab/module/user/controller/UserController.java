package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

  // 인증된 사용자 정보 확인용
  @GetMapping("/me")
  public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserPrincipal userDetails) {
    User user = userDetails.getUser();

    Map<String, Object> response = new HashMap<>();
    response.put("id", user.getId());
    response.put("username", user.getUsername());
    response.put("email", user.getEmail());
    response.put("nickname", user.getNickname());

    return ResponseEntity.ok(response);
  }
}