package com.irnproj.easycollab.module.comment.controller;

import com.irnproj.easycollab.module.comment.dto.CommentRequestDto;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.comment.service.CommentService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  /**
   * 댓글 등록 (원댓글 or 대댓글)
   */
  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    CommentResponseDto response = commentService.createComment(requestDto, userPrincipal.getId());
    return ResponseEntity.ok(response);
  }

  /**
   * 이슈별 댓글 전체 조회 (대댓글 포함)
   */
  @GetMapping("/issues/{issueId}")
  public ResponseEntity<List<CommentResponseDto>> getCommentsByIssue(@PathVariable Long issueId) {
    return ResponseEntity.ok(commentService.getCommentsByIssue(issueId));
  }

  /**
   * 댓글 수정
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateComment(
      @PathVariable Long id,
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    commentService.updateComment(id, requestDto, userPrincipal.getId());
    return ResponseEntity.ok().build();
  }

  /**
   * 댓글 삭제
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteComment(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    commentService.deleteComment(id, userPrincipal.getId());
    return ResponseEntity.noContent().build();
  }

  // 마이페이지: 내가 작성한 댓글 목록
  @GetMapping("/me")
  public ResponseEntity<List<CommentResponseDto>> getMyComments(
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(commentService.getMyComments(userPrincipal.getId()));
  }

}