package com.irnproj.easycollab.module.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

  @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
  private String content;

  @NotNull(message = "이슈 ID는 필수입니다.")
  private Long issueId;

  private Long parentId;  // null이면 원댓글
}