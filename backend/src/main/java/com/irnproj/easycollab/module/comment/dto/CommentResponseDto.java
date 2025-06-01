package com.irnproj.easycollab.module.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentResponseDto {

  private Long id;                    // 댓글 ID
  private String content;             // 댓글 내용
  private String authorName;          // 작성자 닉네임
  private Long authorId;              // 작성자 ID
  private LocalDateTime createdAt;    // 작성일
  private LocalDateTime updatedAt;    // 수정일

  private List<CommentResponseDto> children; // 대댓글 목록 (재귀)
}