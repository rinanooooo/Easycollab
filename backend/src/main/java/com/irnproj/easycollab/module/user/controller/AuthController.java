package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.user.dto.*;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.service.UserService;
import com.irnproj.easycollab.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignupRequestDto dto) {
    userService.register(dto);
    return ResponseEntity.ok("회원가입이 완료되었습니다.");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
    User user = userService.authenticate(request.getLoginId(), request.getPassword());
    String token = jwtUtil.generateToken(user.getLoginId());
    return ResponseEntity.ok(LoginResponseDto.fromEntity(user, token));
  }
}
