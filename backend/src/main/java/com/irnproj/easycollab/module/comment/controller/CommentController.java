package com.irnproj.easycollab.module.comment.controller;

import com.irnproj.easycollab.module.comment.dto.CommentRequestDto;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @Operation(summary = "댓글 생성", description = "이슈 ID와 댓글 내용을 입력하여 댓글을 생성합니다. 대댓글인 경우 부모 댓글 ID를 함께 전달합니다.")
  public CommentResponseDto create(@RequestBody CommentRequestDto requestDto) {
    return commentService.createComment(requestDto);
  }

  @GetMapping("/issue/{issueId}")
  @Operation(summary = "이슈별 댓글 목록 조회", description = "특정 이슈 ID에 해당하는 댓글 목록을 트리 구조로 조회합니다.")
  public List<CommentResponseDto> getComments(@PathVariable Long issueId) {
    return commentService.getCommentsByIssueId(issueId);
  }

  @PutMapping("/{commentId}")
  @Operation(summary = "댓글 수정", description = "댓글 ID와 수정할 내용을 입력하여 댓글을 수정합니다.")
  public CommentResponseDto update(@PathVariable Long commentId,
                                   @RequestBody CommentRequestDto requestDto) {
    return commentService.updateComment(commentId, requestDto);
  }

  @DeleteMapping("/{commentId}")
  @Operation(summary = "댓글 삭제", description = "댓글 ID를 통해 해당 댓글을 삭제합니다. 자식 댓글이 존재하는 경우 삭제할 수 없습니다.")
  public void delete(@PathVariable Long commentId) {
    commentService.deleteComment(commentId);
  }
}