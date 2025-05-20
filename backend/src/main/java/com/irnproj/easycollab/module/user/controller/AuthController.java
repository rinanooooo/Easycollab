package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.user.dto.*;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.service.UserService;
import com.irnproj.easycollab.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  // 회원 가입
  @PostMapping("/signup")
  public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto request) {
    User saved = userService.register(request);
    return ResponseEntity.ok(new SignupResponseDto(saved.getUsername(), "회원가입이 완료되었습니다."));
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    String token = jwtUtil.generateToken(authentication.getName());
    return ResponseEntity.ok(new LoginResponseDto(authentication.getName(), token));
  }
}