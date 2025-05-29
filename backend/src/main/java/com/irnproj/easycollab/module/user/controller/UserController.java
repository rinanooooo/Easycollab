package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.issue.service.IssueService;
import com.irnproj.easycollab.module.user.dto.UserResponseDto;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.service.UserService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  // 인증된 사용자 정보 확인용
  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    User user = userService.findById(userPrincipal.getId());
    return ResponseEntity.ok(UserResponseDto.fromEntity(user));
  }



}