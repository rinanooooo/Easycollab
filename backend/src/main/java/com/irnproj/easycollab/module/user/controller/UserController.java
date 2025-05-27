package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.user.dto.UserInfoDto;
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
  public ResponseEntity<UserInfoDto> getMyInfo(@AuthenticationPrincipal UserPrincipal userDetails) {
    User user = userDetails.getUser();

    UserInfoDto response = new UserInfoDto(
        user.getLoginId(),
        user.getUsername(),
        user.getNickname(),
        user.getEmail(),
        user.getRole().getCode()  // 또는 .getName()
        // getCode(): 시스템/백엔드/권한 체크용
        // getName(): 프론트 UI/화면 출력용
    );

    return ResponseEntity.ok(response);
  }


}