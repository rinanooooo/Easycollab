package com.irnproj.easycollab.module.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

  private String content;     // 댓글 내용
  private Long issueId;       // 연결된 이슈 ID
  private Long parentId;      // 부모 댓글 ID (null이면 원댓글)
}