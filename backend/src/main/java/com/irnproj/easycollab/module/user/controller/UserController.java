package com.irnproj.easycollab.module.user.controller;

import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.issue.service.IssueService;
import com.irnproj.easycollab.module.user.dto.UserResponseDto;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.service.UserService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  // 마이페이지 - 내 정보 조회
  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return ResponseEntity.ok(userService.getUserInfo(userPrincipal.getId()));
  }

  // 마이페이지 - 내가 작성한 댓글 목록 조회
//  @GetMapping("/me/comments")
//  public ResponseEntity<List<CommentResponseDto>> getMyComments(@AuthenticationPrincipal UserPrincipal userPrincipal) {
//    return ResponseEntity.ok(userService.getMyComments(userPrincipal.getId()));
//  }
}